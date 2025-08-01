package com.martmists.multiplatform.parsing.xml

import com.martmists.multiplatform.parsing.core.Lexer
import com.martmists.multiplatform.parsing.xml.ast.*
import com.martmists.multiplatform.parsing.xml.element.Element
import com.martmists.multiplatform.parsing.xml.element.NodeElement
import com.martmists.multiplatform.parsing.xml.element.TextElement

class XMLParser(
    text: String,
    private val ignoreEmptyText: Boolean = true,
    private val additionalValidation: Boolean = true,
) : Lexer(text.trim()) {
    fun parse(): Document {
        val node = parseDocument()
        val root = asElement(node.element)!!
        return Document(root)
    }

    private fun asElement(node: ElementEntry): Element? {
        return when (node) {
            is CDSectNode -> null
            is CharDataNode -> TextElement(node.contents).takeIf { !ignoreEmptyText || node.contents.isNotBlank() }
            is CommentNode -> null
            is ContentElementNode -> NodeElement(node.tag, node.attributes.associate { it.name to it.value }, joinElements(node.elementEntries.mapNotNull(::asElement)))
            is EmptyElementNode -> NodeElement(node.tag, node.attributes.associate { it.name to it.value }, emptyList())
            is PINode -> null
            is EntityRefNode -> TextElement("&${node.name};")
            is PEReferenceNode -> null
        }
    }

    private fun joinElements(elements: List<Element>): List<Element> {
        val items = mutableListOf<Element>()
        var current: TextElement? = null
        for (el in elements) {
            if (el is TextElement) {
                current = if (current == null) {
                    el
                } else {
                    TextElement(current.contents + el.contents)
                }
            } else {
                if (current != null) {
                    items.add(current)
                }
                current = null
                items.add(el)
            }
        }
        if (current != null) {
            items.add(current)
        }
        return items
    }

    fun parseDocument(): DocumentNode {
        val here = loc()
        val prolog = parseProlog()
        val element = parseElement()
        val misc = zeroOrMore { parseMisc() }
        return DocumentNode(prolog, element, misc, here)
    }

    private fun parseProlog(): PrologNode {
        val here = loc()
        val decl = optional { parseXmlDecl() }
        val misc = zeroOrMore { parseMisc() }
        val doctype = optional { parseDoctype() }
        val docMisc = zeroOrMore { parseMisc() }
        return PrologNode(decl, misc, doctype, docMisc, here)
    }

    private fun parseXmlDecl(): DeclNode {
        val here = loc()
        consume("<?xml")
        val version = parseVersionInfo()
        val encoding = optional { parseEncodingDecl() }
        val sd = optional { parseSDDecl() }
        optional { parseS() }
        consume("?>")
        return DeclNode(
            version,
            encoding,
            sd,
            here
        )
    }

    private fun parseVersionInfo(): String {
        parseS()
        consume("version")
        parseEq()
        val quot = attemptTo { consume('\'') } ?: consume('"')
        val res = consumeMatching(Regex("1.[0-9]+"))
        consume(quot)
        return res
    }

    private fun parseEncodingDecl(): String {
        parseS()
        consume("encoding")
        parseEq()
        val quot = attemptTo { consume('\'') } ?: consume('"')
        val encName = consumeMatching(Regex("[A-Za-z][A-Za-z0-9._\\-]*"))
        consume(quot)
        return encName
    }

    private fun parseSDDecl(): Boolean {
        parseS()
        consume("standalone")
        parseEq()
        val quot = attemptTo { consume('\'') } ?: consume('"')
        val isStandalone = consumeMatching(Regex("(yes|no)")) == "yes"
        consume(quot)
        return isStandalone
    }

    private fun parseDoctype(): DocTypeNode {
        val here = loc()
        consume("<!DOCTYPE")
        parseS()
        val name = parseName()
        val externalId = attemptTo {
            parseS()
            parseExternalID()
        }
        optional { parseS() }
        val subset = attemptTo {
            consume('[')
            val v = parseIntSubset()
            consume(']')
            optional { parseS() }
            v
        } ?: emptyList()
        consume('>')
        return DocTypeNode(name, externalId, subset, here)
    }

    private fun parseExternalID(): ExternalIDNode {
        val here = loc()
        return attemptTo {
            consume("SYSTEM")
            parseS()
            val lit = attemptTo { consumeMatching(Regex("\"[^\"]*\"")) } ?: consumeMatching(Regex("'[^']*'"))
            SystemExternalIDNode(lit.substring(1, lit.lastIndex), here)
        } ?: run {
            consume("PUBLIC")
            parseS()
            val pub = attemptTo {
                consume('"')
                val s = consumeMatching(Regex("[ \r\na-zA-Z0-9\\-'()+,./:=?;!*#@\$_%]*"))
                consume('"')
                s
            } ?: run {
                consume('\'')
                val s = consumeMatching(Regex("[ \r\na-zA-Z0-9\\-()+,./:=?;!*#@\$_%]*"))
                consume('\'')
                s
            }
            parseS()
            val lit = attemptTo { consumeMatching(Regex("\"[^\"]*\"")) } ?: consumeMatching(Regex("'[^']*'"))
            PublicExternalIDNode(pub, lit.substring(1, lit.lastIndex), here)
        }
    }

    private fun parseIntSubset(): List<IntSubsetNode> {
        return zeroOrMore {
            attemptTo { parseElementDecl() }
                ?: attemptTo { parseAttListDecl() }
                ?: attemptTo { parseEntityDecl() }
                ?: attemptTo { parseNotationDecl() }
                ?: attemptTo { parsePI() }
                ?: attemptTo { parseComment() }
                ?: attemptTo { parsePEReference() }
                ?: parseS()
        }
    }

    private fun parseEntityDecl(): EntityDeclNode {
        return attemptTo { parseGEDecl() } ?: parsePEDecl()
    }

    private fun parseGEDecl(): GEDeclNode {
        val here = loc()
        consume("<!ENTITIY")
        parseS()
        val name = parseName()
        parseS()
        val def = parseEntityDef()
        optional { parseS() }
        consume('>')
        return GEDeclNode(name, def, here)
    }

    private fun parseEntityDef(): EntityDefNode {
        val here = loc()
        return attemptTo {
            ValueEntityDefNode(parseEntityValue(), here)
        } ?: run {
            val id = parseExternalID()
            val data = optional { parseNDataDecl() }
            IdEntityDefNode(id, data, here)
        }
    }

    private fun parseNDataDecl(): NDataDeclNode {
        val here = loc()
        parseS()
        consume("NDATA")
        parseS()
        val name = parseName()
        return NDataDeclNode(name, here)
    }

    private fun parsePEDef(): PEDefNode {
        return attemptTo { parseEntityValue() } ?: parseExternalID()
    }

    private fun parseEntityValue(): EntityValueNode {
        val here = loc()
        val quot = attemptTo { consume('"') } ?: consume('\'')
        val contents = zeroOrMore {
            attemptTo {
                require(peek() != '%' && peek() != '&' && peek() != quot)
                next().toString()
            } ?: parseReference().let {
                when (it) {
                    is EntityRefNode -> "&${it.name};"
                    is PEReferenceNode -> "%${it.name};"
                }
            }
        }.joinToString("")
        consume(quot)
        return EntityValueNode(contents, here)
    }

    private fun parsePEDecl(): PEDeclNode {
        val here = loc()
        consume("<!ENTITIY")
        parseS()
        consume('%')
        parseS()
        val name = parseName()
        parseS()
        val def = parsePEDef()
        optional { parseS() }
        consume('>')
        return PEDeclNode(name, def, here)
    }

    private fun parseNotationDecl(): NotationDeclNode {
        val here = loc()
        consume("<!NOTATION")
        parseS()
        val name = parseName()
        parseS()
        val id = attemptTo { parseExternalID() } ?: run {
            val here = loc()
            consume("PUBLIC")
            parseS()
            val pub = attemptTo {
                consume('"')
                val s = consumeMatching(Regex("[ \r\na-zA-Z0-9\\-'()+,./:=?;!*#@\$_%]*"))
                consume('"')
                s
            } ?: run {
                consume('\'')
                val s = consumeMatching(Regex("[ \r\na-zA-Z0-9\\-()+,./:=?;!*#@\$_%]*"))
                consume('\'')
                s
            }
            PublicIDNode(pub, here)
        }
        optional { parseS() }
        consume('>')
        return NotationDeclNode(name, id, here)
    }

    private fun parseAttListDecl(): AttListDeclNode {
        val here = loc()
        consume("<!ATTLIST")
        parseS()
        val name = parseName()
        val attrs = zeroOrMore { parseAttDef() }
        optional { parseS() }
        consume('>')
        return AttListDeclNode(name, attrs, here)
    }

    private fun parseAttDef(): AttDefNode {
        val here = loc()
        parseS()
        val name = parseName()
        parseS()
        val type = attemptTo { consume("CDATA") }
            ?: attemptTo { consume("ID") }
            ?: attemptTo { consume("IDREF") }
            ?: attemptTo { consume("IDREFS") }
            ?: attemptTo { consume("ENTITY") }
            ?: attemptTo { consume("ENTITIES") }
            ?: attemptTo { consume("NMTOKEN") }
            ?: attemptTo { consume("NMTOKENS") }
            ?: attemptTo {
                consume("NOTATION") + parseS() + consume('(') + (optional { parseS().content } ?: "") + parseName() + zeroOrMore { (optional { parseS().content } ?: "") + consume('|') + (optional { parseS().content } ?: "") + parseName() }.joinToString("") + (optional { parseS().content } ?: "") + consume(')')
            } ?: run {
                consume('(') + (optional { parseS().content } ?: "") + consumeMatching(nameRegex).also { require(it.isNotEmpty()) } + zeroOrMore { (optional { parseS().content } ?: "") + consume('|') + (optional { parseS().content } ?: "") + consumeMatching(nameRegex).also { require(it.isNotEmpty()) } } + (optional { parseS().content } ?: "") + consume(')')
            }
        parseS()
        val default = attemptTo { consume("#REQUIRED") }
            ?: attemptTo { consume("#IMPLIED") }
            ?: ((optional { consume("#FIXED") + parseS().content } ?: "") + parseAttValue())
        return AttDefNode(name, type, default, here)
    }

    private fun parseElementDecl(): ElementDeclNode {
        val here = loc()
        consume("<!ELEMENT")
        parseS()
        val name = parseName()
        parseS()
        val content = parseContentSpec()
        optional { parseS() }
        consume('>')
        return ElementDeclNode(name, content, here)
    }

    private fun parseContentSpec(): ContentSpecNode {
        val here = loc()
        return attemptTo {
            consume("EMPTY")
            EmptyContentSpecNode(here)
        } ?: attemptTo {
            consume("ANY")
            AnyContentSpecNode(here)
        } ?: attemptTo {
            parseMixed()
        } ?: parseChildren()
    }

    private fun parseMixed(): MixedNode {
        val here = loc()
        consume('(')
        parseS()
        consume("#PCDATA")
        val names = zeroOrMore {
            optional { parseS() }
            consume('|')
            optional { parseS() }
            parseName()
        }
        if (additionalValidation) {
            require(names.toSet().size == names.size)
        }
        optional { parseS() }
        consume(')')
        if (names.isNotEmpty()) {
            consume('*')
        }
        return MixedNode(names, here)
    }

    private fun parseCp(): CpNode {
        val here = loc()
        val node = attemptTo { NameEntryNode(parseName(), here) } ?: attemptTo { parseChoice() } ?: parseSeq()
        val modifier = attemptTo { consume('?') } ?: attemptTo { consume('*') } ?: optional { consume('+') }
        return CpNode(node, modifier, here)
    }

    private fun parseChildren(): ChildrenNode {
        val here = loc()
        val node = attemptTo { parseChoice() } ?: parseSeq()
        val modifier = attemptTo { consume('?') } ?: attemptTo { consume('*') } ?: optional { consume('+') }
        return ChildrenNode(node, modifier, here)
    }

    private fun parseChoice(): ChoiceEntryNode {
        val here = loc()
        consume('(')
        optional { parseS() }
        val item = parseCp()
        val other = zeroOrMore {
            parseS()
            consume('|')
            parseS()
            parseCp()
        }
        parseS()
        consume(')')
        return ChoiceEntryNode(listOf(item, *other.toTypedArray()), here)
    }

    private fun parseSeq(): SeqEntryNode {
        val here = loc()
        consume('(')
        optional { parseS() }
        val item = parseCp()
        val other = zeroOrMore {
            parseS()
            consume(',')
            parseS()
            parseCp()
        }
        parseS()
        consume(')')
        return SeqEntryNode(listOf(item, *other.toTypedArray()), here)
    }

    private fun parseElement(): ElementNode {
        val here = loc()
        return attemptTo {
            // Empty element
            consume('<')
            val tag = parseName()
            val attrs = zeroOrMore {
                parseS()
                parseAttribute()
            }
            optional { parseS() }
            consume("/>")
            EmptyElementNode(tag, attrs, here)
        } ?: run {
            // Element with contents
            consume('<')
            val tag = parseName()
            val attrs = zeroOrMore {
                parseS()
                parseAttribute()
            }
            optional { parseS() }
            consume('>')
            val elements = mutableListOf<ElementEntry>()
            optional { parseCharData() }?.let(elements::add)
            zeroOrMore {
                val item = attemptTo { parseElement() }
                    ?: attemptTo { parseReference() }
                    ?: attemptTo { parseCDSect() }
                    ?: attemptTo { parsePI() }
                    ?: parseComment()
                elements.add(item)
                optional { parseCharData() }?.let(elements::add)
            }
            consume("</")
            consume(tag)
            optional { parseS() }
            consume('>')
            ContentElementNode(tag, attrs, elements, here)
        }
    }

    private fun parseCharData(): CharDataNode {
        val here = loc()
        val content = zeroOrMore {
            require(peek() != ']' && peek2() != ']' && peek3() != '>')
            require(peek() != '<' && peek() != '&')
            next()
        }.joinToString("")
        return CharDataNode(content, here)
    }

    private fun parseCDSect(): CDSectNode {
        val here = loc()
        consume("<![CDATA[")
        val content = zeroOrMore {
            require(peek() != ']' && peek2() != ']' && peek3() != '>')
            parseChar()
        }.joinToString("")
        consume("]]>")
        return CDSectNode(content, here)
    }

    private fun parseAttribute(): AttributeNode {
        val here = loc()
        val name = parseName()
        parseEq()
        val value = parseAttValue()
        return AttributeNode(name, value, here)
    }

    private fun parseAttValue(): String {
        val quot = attemptTo { consume('"') } ?: consume('\'')
        val contents = zeroOrMore {
            attemptTo {
                require(peek() != '<' && peek() != '&' && peek() != quot)
                next().toString()
            } ?: parseReference()
        }
        consume(quot)
        return contents.joinToString("")
    }

    private fun parseReference(): ReferenceNode {
        return attemptTo { parseEntityRef() } ?: parsePEReference()
    }

    private fun parseEntityRef(): EntityRefNode {
        val here = loc()
        consume('&')
        val name = parseName()
        consume(';')
        return EntityRefNode(name, here)
    }

    private fun parsePEReference(): PEReferenceNode {
        val here = loc()
        consume('%')
        val name = parseName()
        consume(';')
        return PEReferenceNode(name, here)
    }

    private fun parseMisc(): MiscNode {
        return attemptTo { parseComment() } ?: attemptTo { parsePI() } ?: parseS()
    }

    private fun parseComment(): CommentNode {
        val here = loc()
        consume("<!--")
        var buffer = ""
        zeroOrMore {
            val s = attemptTo { parseChar().also { require(it != '-') }.toString() } ?: (consume("-") + parseChar().also { require(it != '-') })
            buffer += s
        }
        consume("-->")
        return CommentNode(buffer, here)
    }

    private fun parsePI(): PINode {
        val here = loc()
        consume("<?")
        val target = parseName().also { require(it.lowercase() != "xml") }
        val value = optional {
            parseS()
            zeroOrMore {
                require(peek() != '?' && peek2() != '>')
                parseChar()
            }.joinToString("")
        }
        consume("?>")
        return PINode(
            target,
            value,
            here
        )
    }

    private val wsRegex = Regex("[ \n\t\r]+")
    private fun parseS(): SNode {
        val here = loc()
        return SNode(consumeMatching(wsRegex), here)
    }

    private fun parseEq() {
        optional { parseS() }
        consume('=')
        optional { parseS() }
    }

    private val nameStartRegex = Regex("[:a-z_A-Z\u00C0-\u00D6\u00D8-\u00F6\u00F8-\u02FF\u0370-\u037D\u037F-\u1FFF\u200C\u200D\u2070-\u218F\u2C00-\u2FEF\u3001-\uD7FF\uF900-\uFDCF\uFDF0-\uFFFD]")
    private val nameRegex = Regex("[:a-z_A-Z\u00C0-\u00D6\u00D8-\u00F6\u00F8-\u02FF\u0370-\u037D\u037F-\u1FFF\u200C\u200D\u2070-\u218F\u2C00-\u2FEF\u3001-\uD7FF\uF900-\uFDCF\uFDF0-\uFFFD\\-.0-9\u00B7\u0300-\u036F\u203F\u2040]*")
    private fun parseName(): String {
        val start = consumeMatching(nameStartRegex, false)
        val remaining = consumeMatching(nameRegex, false)
        return start + remaining
    }

    private fun parseChar(): Char {
        val c = next()
        require(c !in Char.MIN_LOW_SURROGATE .. Char.MAX_LOW_SURROGATE)
        require(c !in Char.MIN_HIGH_SURROGATE .. Char.MAX_HIGH_SURROGATE)
        require(c != '\uFFFE' && c != '\uFFFF')
        return c
    }

    companion object {
        fun parse(text: String, ignoreEmptyText: Boolean = true, additionalValidation: Boolean = true): Document = XMLParser(text, ignoreEmptyText, additionalValidation).parse()
    }
}
