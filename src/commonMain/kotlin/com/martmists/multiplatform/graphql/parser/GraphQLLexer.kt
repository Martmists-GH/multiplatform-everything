package com.martmists.multiplatform.graphql.parser

import com.martmists.multiplatform.graphql.parser.ast.*
import com.martmists.multiplatform.parsing.core.Lexer
import kotlin.math.pow

class GraphQLLexer(contents: String) : Lexer(contents) {
    fun parseExecutableDocument(): ExecutableDocument {
        consumeIgnored()
        val loc = loc()
        val defs = oneOrMore {
            parseExecutableDefinition()
        }
        consumeEOF()
        return ExecutableDocument(defs, loc)
    }

    override fun consumeIgnored() {
        while (true) {
            if (peek() == ',') {
                next()
                continue
            }
            if (peek() == '#') {
                next()
                while (peek() != '\n') next()
                continue
            }
            if (peek()?.isWhitespace() == true) {
                next()
                continue
            }
            if (peek() == '\uFEFF') {
                next()
                continue
            }
            break
        }
    }

    private fun parseExecutableDefinition(): ExecutableDefinition {
        return attemptTo { parseOperationDefinition() }
            ?: parseFragmentDefinition()
    }

    private fun parseOperationDefinition(): OperationDefinition {
        return attemptTo {
            val loc = loc()
            val type = parseOperationType()
            val name = attemptTo { parseName() }
            val varDef = attemptTo { parseVariablesDefinition() } ?: emptyList()
            val dirs = attemptTo { parseDirectives() } ?: emptyList()
            val select = parseSelectionSet()

            OperationDefinition(type, name, varDef, dirs, select, loc)
        } ?: let {
            val loc = loc()
            val select = parseSelectionSet()
            OperationDefinition(OperationType.QUERY, null, emptyList(), emptyList(), select, loc)
        }
    }

    private fun parseOperationType(): OperationType {
        return attemptTo { consume("query"); OperationType.QUERY }
            ?: attemptTo { consume("mutation"); OperationType.MUTATION }
            ?: let { consume("subscription"); OperationType.SUBSCRIPTION }
    }

    private fun parseSelectionSet(): List<Selection> {
        consume('{')
        val res = oneOrMore { parseSelection() }
        consume('}')
        return res
    }

    private fun parseSelection(): Selection {
        return attemptTo { parseField() }
            ?: attemptTo { parseFragmentSpread() }
            ?: parseInlineFragment()
    }

    private fun parseField(): Field {
        val loc = loc()
        val alias = attemptTo { parseAlias() }
        val name = parseName()
        val args = attemptTo { parseArguments() } ?: emptyList()
        val dirs = attemptTo { parseDirectives() } ?: emptyList()
        val select = attemptTo { parseSelectionSet() }
        return Field(alias, name, args, dirs, select ?: emptyList(), loc)
    }

    private fun parseArguments(): List<Argument> {
        consume('(')
        val res = oneOrMore { parseArgument() }
        consume(')')
        return res
    }

    private fun parseArgument(): Argument {
        val loc = loc()
        val name = parseName()
        consume(':')
        val value = parseValue()
        return Argument(name, value, loc)
    }

    fun parseAlias(): String {
        val name = parseName()
        consume(':')
        return name
    }

    private fun parseFragmentSpread(): FragmentSpread {
        val loc = loc()
        consume("...")
        val name = parseFragmentName()
        val dirs = attemptTo { parseDirectives() } ?: emptyList()
        return FragmentSpread(name, dirs, loc)
    }

    private fun parseFragmentDefinition(): FragmentDefinition {
        val loc = loc()
        consume("fragment")
        val name = parseFragmentName()
        val typeCond = parseTypeCondition()
        val dirs = attemptTo { parseDirectives() } ?: emptyList()
        val select = parseSelectionSet()
        return FragmentDefinition(name, typeCond, dirs, select, loc)
    }

    private fun parseFragmentName(): String {
        val name = parseName()
        require(name != "on") { "Invalid fragment name" }
        return name
    }

    private fun parseTypeCondition(): NamedType {
        val loc = loc()
        consume("on")
        return NamedType(parseName(), loc)
    }

    private fun parseInlineFragment(): InlineFragment {
        val loc = loc()
        consume("...")
        val typeCond = attemptTo { parseTypeCondition() }
        val dirs = attemptTo { parseDirectives() } ?: emptyList()
        val select = parseSelectionSet()
        return InlineFragment(typeCond, dirs, select, loc)
    }

    private fun parseName(): String {
        val res = parseNameStart() + zeroOrMore { parseNameContinue() }.joinToString("")
        consumeIgnored()
        return res
    }

    private fun parseNameStart(): String {
        return attemptTo { parseLetter() } ?: consume('_', skip = false).toString()
    }

    private fun parseNameContinue(): String {
        val res = attemptTo { parseDigit() } ?: parseNameStart()
        return res
    }

    private fun parseValue(): Value {
        return attemptTo { parseVariable() }
            ?: attemptTo { parseIntValue() }
            ?: attemptTo { parseFloatValue() }
            ?: attemptTo { parseStringValue() }
            ?: attemptTo { parseBooleanValue() }
            ?: attemptTo { parseNullValue() }
            ?: attemptTo { parseEnumValue() }
            ?: attemptTo { parseListValue() }
            ?: parseObjectValue()
    }

    private fun parseIntValue(): IntValue {
        val loc = loc()
        val num = consumeMatching(Regex("-?(0|[1-9]\\d*)"))
        val res = num.toLong()

        val lookahead = attemptTo { consume('.') } ?: attemptTo { parseNameStart() }
        require(lookahead == null) { "Unexpected character after int" }

        return IntValue(res, loc)
    }

    private fun parseFloatValue(): FloatValue {
        val loc = loc()
        val int = consumeMatching(Regex("-?(0|[1-9]\\d*)"), skip = false)
        val frac = attemptTo { consumeMatching(Regex("\\.\\d+"), skip = false) }
        val exp = attemptTo { consumeMatching(Regex("[eE]"), skip = false); consumeMatching(Regex("[\\-+]\\d+"), skip = false).toInt() }
        require(frac != null || exp != null) { "Expected fractional or exponent part" }
        val fracPart = frac ?: ".0"
        val floatBase = (int + fracPart).toFloat()
        val factor = 10f.pow(exp ?: 0)
        return FloatValue(floatBase * factor, loc)
    }

    private fun parseBooleanValue(): BooleanValue {
        val loc = loc()
        val value = consumeMatching(Regex("true|false"))
        return BooleanValue(value.toBooleanStrict(), loc)
    }

    private fun parseStringValue(): StringValue {
        val loc = loc()
        val str = attemptTo { parseBlockString() }
            ?: attemptTo { consume("\"\""); peek() != '"'; "" }
            ?: let {
                consume('"', skip = false)
                val sb = StringBuilder()
                while (true) {
                    val c = next()
                    if (c == '\\') {
                        sb.append(c)
                        sb.append(next())
                    } else if (c == '\n') {
                        throw IllegalArgumentException("Unexpected end of line in string")
                    } else if (c == '"') {
                        break
                    } else {
                        sb.append(c)
                    }
                }
                sb.toString()
            }
        return StringValue(str.evaluate(), loc)
    }

    private fun parseBlockString(): String {
        consume("\"\"\"")
        val str = StringBuilder()
        while (true) {
            val c = next()
            if (c == '"') {
                if (peek() == '"' && peek2() == '"') {
                    i += 2
                    break
                }
            } else if (c == '\\') {
                if (peek() == '"' && peek2() == '"' && peek3() == '"') {
                    i += 3
                    str.append("\"\"\"")
                    continue
                }
            }
            str.append(c)
        }
        return str.toString()
    }

    private fun String.evaluate(): String {
        val sb = StringBuilder()
        for (c in this) {
            if (c == '\\') {
                val c2 = next()
                when (c2) {
                    '"' -> sb.append('"')
                    '\\' -> sb.append('\\')
                    '/' -> sb.append('/')
                    'b' -> sb.append('\b')
                    'f' -> sb.append('\u000C')
                    'n' -> sb.append('\n')
                    'r' -> sb.append('\r')
                    't' -> sb.append('\t')
                    'u' -> {
                        val hex = attemptTo { consume('{'); val res = consumeMatching(Regex("[\\da-fA-F]+")); consume('}'); res }
                            ?: consumeMatching(Regex("[\\da-fA-F]{4}"))
                        val num = hex.toInt(16)
                        if (num > 0xFFFF) {
                            // Unicode surrogate pair
                            val high = (num and 0xFFFF0000.toInt()) shr 16
                            val low = num and 0xFFFF
                            sb.append(high.toChar())
                            sb.append(low.toChar())
                        } else {
                            sb.append(num.toChar())
                        }
                    }
                }
            } else {
                sb.append(c)
            }
        }
        return sb.toString()
    }

    private fun parseNullValue(): NullValue {
        val loc = loc()
        consume("null")
        return NullValue(loc)
    }

    private fun parseEnumValue(): EnumValue {
        val loc = loc()
        val name = parseName()
        require(name !in arrayOf("true", "false", "null")) { "Invalid enum value: $name" }
        return EnumValue(name, loc)
    }

    private fun parseListValue(): ListValue {
        val loc = loc()
        consume('[')
        val items = zeroOrMore {
            parseValue()
        }
        consume(']')
        return ListValue(items, loc)
    }

    private fun parseObjectValue(): ObjectValue {
        val loc = loc()
        consume('{')
        val elems = zeroOrMore {
            val name = parseName()
            consume(':')
            val value = parseValue()
            name to value
        }.toMap()
        consume('}')
        return ObjectValue(elems, loc)
    }

    private fun parseVariable(): Variable {
        val loc = loc()
        consume('$')
        return Variable(parseName(), loc)
    }

    private fun parseVariablesDefinition(): List<VariableDefinition> {
        consume('(')
        val variables = zeroOrMore {
            parseVariableDefinition()
        }
        consume(')')
        return variables
    }

    private fun parseVariableDefinition(): VariableDefinition {
        val loc = loc()
        val target = parseVariable()
        consume(':')
        val type = parseType()
        val default = attemptTo { parseValue() }
        val dirs = attemptTo { parseDirectives() } ?: emptyList()
        return VariableDefinition(target, type, default, dirs, loc)
    }

    private fun parseType(): Type {
        val loc = loc()
        val type = attemptTo { NamedType(parseName(), loc) }
            ?: let { consume('['); val t = parseType(); consume(']'); ListType(t, loc) }
        if (attemptTo { consume('!') } != null) {
            return NonNullType(type, loc)
        }
        return type
    }

    private fun parseDirectives(): List<Directive> {
        return oneOrMore {
            parseDirective()
        }
    }

    private fun parseDirective(): Directive {
        val loc = loc()
        consume('@')
        val name = parseName()
        val args = attemptTo { parseArguments() } ?: emptyList()
        return Directive(name, args, loc)
    }

    private fun parseLetter(): String {
        return consumeMatching(Regex("[a-zA-Z]"), skip = false)
    }

    private fun parseDigit(): String {
        return consumeMatching(Regex("\\d"), skip = false)
    }
}
