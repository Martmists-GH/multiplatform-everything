package com.martmists.multiplatform.validation.lexer

import com.martmists.multiplatform.parsing.core.ABNFLexer

class EmailLexer(contents: String, private val stripComments: Boolean = false) : ABNFLexer(contents) {
    fun consumeAddress(): String {
        val result = attemptTo { consumeMailbox() }
            ?: consumeGroup()

        consumeEOF()

        return result
    }

    fun consumeMailbox(): String {
        return attemptTo { consumeNameAddr() }
            ?: consumeAddrSpec()
    }

    fun consumeNameAddr(): String {
        return optionalStr { consumeDisplayName() } + consumeAngleAddr()
    }

    fun consumeAngleAddr(): String {
        return attemptTo {
            optionalStr { consumeCfws() } + consume('<') + consumeAddrSpec() + consume('>') + optionalStr { consumeCfws() }
        } ?: consumeObsAngleAddr()
    }

    fun consumeGroup(): String {
        return consumeDisplayName() + consume(':') + optionalStr { consumeGroupList() } + consume(';') + optionalStr { consumeCfws() }
    }

    fun consumeDisplayName(): String {
        return consumePhrase()
    }

    fun consumeMailboxList(): String {
        return attemptTo { consumeMailbox() + zeroOrMoreStr { consume(',') + consumeMailbox() } }
            ?: consumeObsMboxList()
    }

    fun consumeGroupList(): String {
        return attemptTo { consumeMailboxList() }
            ?: attemptTo { consumeCfws() }
            ?: consumeObsGroupList()
    }

    fun consumeAddrSpec(): String {
        return consumeLocalPart() + consume('@') + consumeDomain()
    }

    fun consumeLocalPart(): String {
        return attemptTo { consumeDotAtom() }
            ?: attemptTo { consumeQuotedString() }
            ?: consumeObsLocalPart()
    }

    private fun validateDomain(domain: String) {
        if (!stripComments) {
            EmailLexer(domain, true).consumeDomain()
        } else {
            if (domain.matches("[0-9.]+".toRegex())) {
                URILexer(domain).consumeIpv4Address()
            } else if (domain.matches("[0-9a-fA-F:]+".toRegex())) {
                URILexer(domain).consumeIpv6Address()
            } else {
                // FIXME: This doesn't support UTF-8 domains
                // DomainLexer(domain).consumeDomain()
            }
        }
    }

    fun consumeDomain(): String {
        return attemptTo { consumeDotAtom().also { validateDomain(it) } }
            ?: attemptTo { consumeDomainLiteral() }
            ?: consumeObsDomain().also { validateDomain(it) }
    }

    fun consumeDomainLiteral(): String {
        val domainLiteral = optionalStr { consumeCfws() } +
                consume('[') +
                zeroOrMoreStr { optionalStr { consumeFws() } + consumeDtext() } +
                optionalStr { consumeFws() } +
                consume(']') +
                optionalStr { consumeCfws() }

        if (stripComments) {
            EmailLexer(domainLiteral, true).consumeDomain()
        } else {
            if (domainLiteral.contains(":")) {
                URILexer(domainLiteral.substring(1, domainLiteral.length - 1)).consumeIpv6Address()
            } else {
                URILexer(domainLiteral.substring(1, domainLiteral.length - 1)).consumeIpv4Address()
            }
        }

        return domainLiteral
    }

    fun consumeDtext(): String {
        return attemptTo { consumeMatching("[\\x21-\\x5A\\x5E-\\x7E]".toRegex()) }
            ?: consumeObsDtext()
    }

    fun consumeWord(): String {
        return attemptTo { consumeAtom() }
            ?: consumeQuotedString()
    }

    fun consumePhrase(): String {
        return attemptTo { oneOrMoreStr { consumeWord() } }
            ?: consumeObsPhrase()
    }

    fun consumeQtext(): String {
        return attemptTo { consumeMatching("[\\x21\\x23-\\x5B\\x5D-\\x7E]".toRegex()) }
            ?: consumeObsQtext()
    }

    fun consumeQcontent(): String {
        return attemptTo { consumeQtext() }
            ?: consumeQuotedPair()
    }

    fun consumeQuotedString(): String {
        return optionalStr { consumeCfws() } + consumeDquote() + zeroOrMoreStr { optionalStr { consumeFws() } + consumeQcontent() } + optionalStr { consumeFws() } + consumeDquote() + optionalStr { consumeCfws() }
    }

    fun consumeAtext(): String {
        return attemptTo { consumeUTF8NonAscii() } ?: consumeMatching("[a-zA-Z0-9!#$%&'*+\\-/=?^_`{|}~]".toRegex())
    }

    fun consumeUTF8NonAscii(): String {
        return attemptTo { consumeUTF8_2() } ?: attemptTo { consumeUTF8_3() } ?: consumeUTF8_4()
    }

    fun consumeUTF8_2(): String {
        return consumeMatching("[\\xC2-\\xDF]]".toRegex()) + consumeUTF8Tail()
    }

    fun consumeUTF8_3(): String {
        return attemptTo { consume('\u00E0') + consumeMatching("[\\xA0-\\xBF]".toRegex()) + consumeUTF8Tail() }
            ?: attemptTo { consumeMatching("[\\xE1-\\xEC]".toRegex()) + repeatedStr(2) { consumeUTF8Tail() } }
            ?: attemptTo { consume('\u00ED') + consumeMatching("[\\x80-\\x9F]".toRegex()) + consumeUTF8Tail() }
            ?: (consumeMatching("[\\xE1-\\xEF]".toRegex()) + repeatedStr(2) { consumeUTF8Tail() })
    }

    fun consumeUTF8_4(): String {
        return attemptTo { consume('\u00F0') + consumeMatching("[\\x90-\\xBF]".toRegex()) + repeatedStr(2) { consumeUTF8Tail() } }
            ?: attemptTo { consumeMatching("[\\xF1\\xF3]".toRegex()) + repeatedStr(3) { consumeUTF8Tail() } }
            ?: (consume('\u00F4') + consumeMatching("[\\x80-\\x8F]".toRegex()) + repeatedStr(2) { consumeUTF8Tail() })
    }

    fun consumeUTF8Tail(): String {
        return consumeMatching("[\\x80-\\xBF]".toRegex())
    }

    fun consumeAtom(): String {
        return optionalStr { consumeCfws() } + oneOrMoreStr { consumeAtext() } + optionalStr { consumeCfws() }
    }

    fun consumeDotAtomText(): String {
        return oneOrMoreStr { consumeAtext() } + zeroOrMoreStr { consume('.') + oneOrMoreStr { consumeAtext() } }
    }

    fun consumeDotAtom(): String {
        return optionalStr { consumeCfws() } + consumeDotAtomText() + optionalStr { consumeCfws() }
    }

    fun consumeFws(): String {
        return attemptTo { optionalStr { zeroOrMoreStr { consumeWsp() } + consumeCrlf() } + oneOrMoreStr { consumeWsp() } }
            ?: consumeObsFws()
    }

    fun consumeCtext(): String {
        return attemptTo {consumeMatching("[\\x21-\\x27\\x2A-\\x5B\\x5D-\\x7E]".toRegex()) }
            ?: consumeObsCtext()
    }

    fun consumeCcontent(): String {
        return attemptTo {consumeCtext() }
            ?: attemptTo {consumeQuotedPair() }
            ?: consumeComment()
    }

    fun consumeComment(): String {
        val comment = consume('(') + zeroOrMoreStr { optionalStr { consumeFws() } + consumeCcontent() }  + optionalStr { consumeFws() } + consume(')')
        return if (stripComments) "" else comment
    }

    fun consumeCfws(): String {
        val cfws = attemptTo { oneOrMoreStr { optionalStr { consumeFws() } + consumeComment() } + optionalStr { consumeFws() } }
            ?: consumeFws()
        return if (stripComments) "" else cfws
    }

    fun consumeQuotedPair(): String {
        return attemptTo { consume('\\') + (attemptTo { consumeVchar() } ?: consumeWsp()) }
            ?: consumeObsQp()
    }

    fun consumeObsNoWsCtl(): String {
        return consumeMatching("[\\x01-\\x08\\x0B\\x0C\\x0E-\\x1F\\x7F]".toRegex())
    }

    fun consumeObsCtext(): String {
        return consumeObsNoWsCtl()
    }

    fun consumeObsQtext(): String {
        return consumeObsNoWsCtl()
    }

    fun consumeObsQp(): String {
        return consume('\\') + (
            attemptTo { consume('\u0000').toString() }
                ?: attemptTo { consumeObsNoWsCtl() }
                ?: attemptTo { consumeLf() }
                ?: consumeCr()
        )
    }

    fun consumeObsPhrase(): String {
        return consumeWord() + zeroOrMoreStr {
            attemptTo { consumeWord() }
                ?: attemptTo { consume('.').toString() }
                ?: consumeCfws()
        }
    }

    fun consumeObsFws(): String {
        return oneOrMoreStr { consumeWsp() } + zeroOrMoreStr { consumeCrlf() + oneOrMoreStr { consumeWsp() } }
    }

    fun consumeObsAngleAddr(): String {
        return optionalStr { consumeCfws() } + consume('<') + consumeObsRoute() + consumeAddrSpec() + consume('>') + optionalStr { consumeCfws() }
    }

    fun consumeObsRoute(): String {
        return consumeObsDomainList() + consume(':')
    }

    fun consumeObsDomainList(): String {
        return zeroOrMoreStr { attemptTo { consumeCfws() } ?: consume(',').toString() } + consume('@') + consumeDomain() +
                zeroOrMoreStr { consume(',') + optionalStr { consumeCfws() } + optionalStr { consume('@') + consumeDomain() } }
    }

    fun consumeObsMboxList(): String {
        return zeroOrMoreStr { optionalStr { consumeCfws() } + consume(',') } + consumeMailbox() +
                zeroOrMoreStr { consume(',') + optionalStr { attemptTo { consumeMailbox() } ?: consumeCfws() } }
    }

    fun consumeObsGroupList(): String {
        return oneOrMoreStr { optionalStr { consumeCfws() } + consume(',') } + optionalStr { consumeCfws() }
    }

    fun consumeObsLocalPart(): String {
        return consumeWord() + zeroOrMoreStr { consume('.') + consumeWord() }
    }

    fun consumeObsDomain(): String {
        return consumeAtom() + zeroOrMoreStr { consume('.') + consumeAtom() }
    }

    fun consumeObsDtext(): String {
        return attemptTo { consumeObsNoWsCtl() }
            ?: consumeQuotedPair()
    }

    fun zeroOrMoreStr(block: () -> String) = zeroOrMore { block() }.joinToString("")
    fun oneOrMoreStr(block: () -> String) = oneOrMore { block() }.joinToString("")
    fun optionalStr(block: () -> String) = optional { block() }?.toString() ?: ""
    fun repeatedStr(n: Int, block: () -> String) = repeated(n, block).joinToString("")
}
