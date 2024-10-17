package com.martmists.multiplatform.graphql.parser

import com.martmists.multiplatform.graphql.SchemaRequestContext
import kotlin.math.pow

data class ExecutableDocument(val definitions: List<ExecutableDefinition>)
sealed interface ExecutableDefinition
data class OperationDefinition(
    val type: OperationType,
    val name: String?,
    val variableDefinitions: VariablesDefinition,
    val directives: Directives,
    val selectionSet: SelectionSet
) : ExecutableDefinition
data class FragmentDefinition(
    val name: String,
    val typeCond: NamedType,
    val directives: Directives,
    val selectionSet: SelectionSet
) : ExecutableDefinition
enum class OperationType {
    QUERY, MUTATION, SUBSCRIPTION
}
typealias SelectionSet = List<Selection>
sealed interface Selection
data class Field(
    val alias: String?,
    val name: String,
    val arguments: Arguments,
    val directives: Directives,
    val selectionSet: SelectionSet
) : Selection
typealias Arguments = List<Argument>
data class Argument(
    val name: String,
    val value: Value
)
sealed interface Value {
    fun on(context: SchemaRequestContext): Any?
}
data class FragmentSpread(
    val name: String,
    val directives: Directives
) : Selection
data class InlineFragment(
    val typeCond: Type?,
    val directives: Directives,
    val selectionSet: SelectionSet
) : Selection
data class IntValue(val value: Long) : Value {
    override fun on(context: SchemaRequestContext): Any? {
        return value
    }
}
data class FloatValue(val value: Float) : Value {
    override fun on(context: SchemaRequestContext): Any? {
        return value
    }
}
data class BooleanValue(val value: Boolean) : Value {
    override fun on(context: SchemaRequestContext): Any? {
        return value
    }
}
data class StringValue(val value: String) : Value {
    override fun on(context: SchemaRequestContext): Any? {
        return value
    }
}
data object NullValue : Value {
    override fun on(context: SchemaRequestContext): Any? {
        return null
    }
}
data class EnumValue(val value: String) : Value {
    override fun on(context: SchemaRequestContext): Any? {
        TODO()
    }
}
data class ListValue(val value: List<Value>) : Value {
    override fun on(context: SchemaRequestContext): Any? {
        return value.map { it.on(context) }
    }
}
data class ObjectValue(val value: Map<String, Value>) : Value {
    override fun on(context: SchemaRequestContext): Any? {
        return value.mapValues { it.value.on(context) }
    }
}
data class Variable(val name: String) : Value {
    override fun on(context: SchemaRequestContext): Any? {
        TODO()
    }
}
typealias VariablesDefinition = List<VariableDefinition>
data class VariableDefinition(
    val variable: Variable,
    val type: Type,
    val defaultValue: Value?,
    val directives: Directives
)
sealed interface Type
data class NonNullType(val type: Type) : Type
data class ListType(val type: Type) : Type
data class NamedType(val name: String) : Type
typealias Directives = List<Directive>
data class Directive(
    val name: String,
    val arguments: Arguments
)

class GraphQLLexer(contents: String) : Lexer(contents) {
    fun parseExecutableDocument(): ExecutableDocument {
        consumeIgnored()
        val defs = oneOrMore {
            parseExecutableDefinition()
        }
        consumeEOF()
        return ExecutableDocument(defs)
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
            val type = parseOperationType()
            val name = attemptTo { parseName() }
            val varDef = attemptTo { parseVariablesDefinition() } ?: emptyList()
            val dirs = attemptTo { parseDirectives() } ?: emptyList()
            val select = parseSelectionSet()

            OperationDefinition(type, name, varDef, dirs, select)
        } ?: let {
            val select = parseSelectionSet()
            OperationDefinition(OperationType.QUERY, null, emptyList(), emptyList(), select)
        }
    }

    private fun parseOperationType(): OperationType {
        return attemptTo { consume("query"); OperationType.QUERY }
            ?: attemptTo { consume("mutation"); OperationType.MUTATION }
            ?: let { consume("subscription"); OperationType.SUBSCRIPTION }
    }

    private fun parseSelectionSet(): SelectionSet {
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
        val alias = attemptTo { parseAlias() }
        val name = parseName()
        val args = attemptTo { parseArguments() } ?: emptyList()
        val dirs = attemptTo { parseDirectives() } ?: emptyList()
        val select = attemptTo { parseSelectionSet() }
        return Field(alias, name, args, dirs, select ?: emptyList())
    }

    private fun parseArguments(): Arguments {
        consume('(')
        val res = oneOrMore { parseArgument() }
        consume(')')
        return res
    }

    private fun parseArgument(): Argument {
        val name = parseName()
        consume(':')
        val value = parseValue()
        return Argument(name, value)
    }

    fun parseAlias(): String {
        val name = parseName()
        consume(':')
        return name
    }

    private fun parseFragmentSpread(): FragmentSpread {
        consume("...")
        val name = parseFragmentName()
        val dirs = attemptTo { parseDirectives() } ?: emptyList()
        return FragmentSpread(name, dirs)
    }

    private fun parseFragmentDefinition(): FragmentDefinition {
        consume("fragment")
        val name = parseFragmentName()
        val typeCond = parseTypeCondition()
        val dirs = attemptTo { parseDirectives() } ?: emptyList()
        val select = parseSelectionSet()
        return FragmentDefinition(name, typeCond, dirs, select)
    }

    private fun parseFragmentName(): String {
        val name = parseName()
        require(name != "on") { "Invalid fragment name" }
        return name
    }

    private fun parseTypeCondition(): NamedType {
        consume("on")
        return NamedType(parseName())
    }

    private fun parseInlineFragment(): InlineFragment {
        consume("...")
        val typeCond = attemptTo { parseTypeCondition() }
        val dirs = attemptTo { parseDirectives() } ?: emptyList()
        val select = parseSelectionSet()
        return InlineFragment(typeCond, dirs, select)
    }

    private fun parseName(): String {
        val res = parseNameStart() + zeroOrMore { parseNameContinue() }.joinToString("")
        consumeIgnored()
        return res
    }

    private fun parseNameStart(): String {
        return attemptTo { parseLetter() } ?: consume('_').toString()
    }

    private fun parseNameContinue(): String {
        val res = attemptTo { parseDigit() } ?: parseNameStart()
        consumeIgnored()
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
        val num = consumeMatching(Regex("-?(0|[1-9]\\d*)"))
        val res = num.toLong()

        val lookahead = attemptTo { consume('.') } ?: attemptTo { parseNameStart() }
        require(lookahead == null) { "Unexpected character after int" }

        return IntValue(res)
    }

    private fun parseFloatValue(): FloatValue {
        val int = consumeMatching(Regex("-?(0|[1-9]\\d*)"), skip = false)
        val frac = attemptTo { consumeMatching(Regex("\\.\\d+"), skip = false) }
        val exp = attemptTo { consumeMatching(Regex("[eE]"), skip = false); consumeMatching(Regex("[\\-+]\\d+"), skip = false).toInt() }
        require(frac != null || exp != null) { "Expected fractional or exponent part" }
        val fracPart = frac ?: ".0"
        val floatBase = (int + fracPart).toFloat()
        val factor = 10f.pow(exp ?: 0)
        return FloatValue(floatBase * factor)
    }

    private fun parseBooleanValue(): BooleanValue {
        val value = consumeMatching(Regex("true|false"))
        return BooleanValue(value.toBooleanStrict())
    }

    private fun parseStringValue(): StringValue {
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
        return StringValue(str.evaluate())
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
        consume("null")
        return NullValue
    }

    private fun parseEnumValue(): EnumValue {
        val name = parseName()
        require(name !in arrayOf("true", "false", "null")) { "Invalid enum value: $name" }
        return EnumValue(name)
    }

    private fun parseListValue(): ListValue {
        consume('[')
        val items = zeroOrMore {
            parseValue()
        }
        consume(']')
        return ListValue(items)
    }

    private fun parseObjectValue(): ObjectValue {
        consume('{')
        val elems = zeroOrMore {
            val name = parseName()
            consume(':')
            val value = parseValue()
            name to value
        }.toMap()
        consume('}')
        return ObjectValue(elems)
    }

    private fun parseVariable(): Variable {
        consume('$')
        return Variable(parseName())
    }

    private fun parseVariablesDefinition(): VariablesDefinition {
        consume('(')
        val variables = zeroOrMore {
            parseVariableDefinition()
        }
        consume(')')
        return variables
    }

    private fun parseVariableDefinition(): VariableDefinition {
        val target = parseVariable()
        consume(':')
        val type = parseType()
        val default = attemptTo { parseValue() }
        val dirs = attemptTo { parseDirectives() } ?: emptyList()
        return VariableDefinition(target, type, default, dirs)
    }

    private fun parseType(): Type {
        val type = attemptTo { NamedType(parseName()) }
            ?: let { consume('['); val t = parseType(); consume(']'); ListType(t) }
        if (attemptTo { consume('!') } != null) {
            return NonNullType(type)
        }
        return type
    }

    private fun parseDirectives(): Directives {
        return oneOrMore {
            parseDirective()
        }
    }

    private fun parseDirective(): Directive {
        consume('@')
        val name = parseName()
        val args = attemptTo { parseArguments() } ?: emptyList()
        return Directive(name, args)
    }

    private fun parseLetter(): String {
        return consumeMatching(Regex("[a-zA-Z]"), skip = false)
    }

    private fun parseDigit(): String {
        return consumeMatching(Regex("\\d"), skip = false)
    }
}
