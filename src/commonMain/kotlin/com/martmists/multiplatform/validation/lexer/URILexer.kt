package com.martmists.multiplatform.validation.lexer

import com.martmists.multiplatform.parsing.core.ABNFLexer

class URILexer(contents: String) : ABNFLexer(contents) {
    fun consumeURI(): String {
        val uri = StringBuilder()
        uri.append(consumeScheme())
        uri.append(consume(':'))
        uri.append(consumeHierPart())
        attemptTo { consume('?') + consumeQuery() }?.let { uri.append(it) }
        attemptTo { consume('#') + consumeFragment() }?.let { uri.append(it) }
        consumeEOF()
        return uri.toString()
    }

    fun consumeScheme(): String {
        return consumeAlpha() + zeroOrMoreStr {
            attemptTo { consumeAlpha() }
                ?: attemptTo { consumeDigit() }
                ?: attemptTo { consume('+').toString() }
                ?: attemptTo { consume('-').toString() }
                ?: consume('.').toString()
        }
    }

    fun consumeHierPart(): String {
        return attemptTo { consume("//") + consumeAuthority() + consumePathAbempty() }
            ?: attemptTo { consumePathAbsolute() }
            ?: attemptTo { consumePathRootless() }
            ?: consumePathEmpty()
    }

    fun consumeQuery(): String {
        return zeroOrMoreStr {
            attemptTo { consumePchar() }
                ?: attemptTo { consume('/').toString() }
                ?: consume('?').toString()
        }
    }

    fun consumeFragment(): String {
        return zeroOrMoreStr {
            attemptTo { consumePchar() }
                ?: attemptTo { consume('/').toString() }
                ?: consume('?').toString()
        }
    }

    fun consumePathAbempty(): String {
        return zeroOrMoreStr {
            consume('/') + consumeSegment()
        }
    }

    fun consumePathAbsolute(): String {
        return consume('/') + optionalStr {
            consumeSegmentNz() + zeroOrMoreStr {
                consume('/') + consumeSegment()
            }
        }
    }

    fun consumePathRootless(): String {
        return consumeSegmentNz() + zeroOrMoreStr {
            consume('/') + consumeSegment()
        }
    }

    fun consumePathEmpty(): String {
        require(attemptTo { consumePchar() } == null) { "Expected empty path at position $i" }
        return ""
    }

    fun consumeSegmentNz(): String {
        return oneOrMoreStr { consumePchar() }
    }

    fun consumeSegment(): String {
        return zeroOrMoreStr { consumePchar() }
    }

    fun consumeAuthority(): String {
        val userinfo = attemptTo { consumeUserinfo() + consume('@') } ?: ""
        val host = consumeHost()
        val port = attemptTo { consume(':') + consumePort() } ?: ""

        return "$userinfo$host$port"
    }

    fun consumeUserinfo() = zeroOrMoreStr {
        attemptTo { consumeUnreserved() }
            ?: attemptTo { consumePctEncoded() }
            ?: attemptTo { consumeSubDelims() }
            ?: consume(':').toString()
    }

    fun consumeHost(): String {
        return attemptTo { consumeIpLiteral() }
            ?: attemptTo { consumeIpv4Address() }
            ?: consumeRegName()
    }

    fun consumePort(): String {
        return zeroOrMoreStr { consumeDigit() }
    }

    fun consumeRegName(): String {
        val regName = zeroOrMoreStr {
            attemptTo { consumeUnreserved() }
                ?: attemptTo { consumePctEncoded() }
                ?: consumeSubDelims()
        }
        require(regName.matches(".*[^0-9.].*".toRegex())) { "Invalid reg-name at position $i" }
        return regName
    }

    fun consumeIpLiteral(): String {
        consume('[')
        val addr = attemptTo { consumeIpv6Address() }
            ?: consumeIpvFuture()
        consume(']')
        return "[$addr]"
    }

    fun consumeIpv6Address(): String {
        return attemptTo { repeatedStr(6) { consumeH16() + consume(':') } + consumeLs32() }
            ?: attemptTo { consume("::") + repeatedStr(5) { consumeH16() + consume(":") } + consumeLs32() }
            ?: attemptTo { optionalStr { consumeH16() } + consume("::") + repeatedStr(4) { consumeH16() + consume(":") } + consumeLs32() }
            ?: attemptTo { optionalStr { betweenStr(0, 1) { consumeH16() + consume(":") + lookAheadMatches("[^:]".toRegex()) } + consumeH16() } + consume("::") + repeatedStr(3) { consumeH16() + consume(":") } + consumeLs32() }
            ?: attemptTo { optionalStr { betweenStr(0, 2) { consumeH16() + consume(":") + lookAheadMatches("[^:]".toRegex()) } + consumeH16() } + consume("::") + repeatedStr(2) { consumeH16() + consume(":") } + consumeLs32() }
            ?: attemptTo { optionalStr { betweenStr(0, 3) { consumeH16() + consume(":") + lookAheadMatches("[^:]".toRegex()) } + consumeH16() } + consume("::") + consumeH16() + consume(":") + consumeLs32() }
            ?: attemptTo { optionalStr { betweenStr(0, 4) { consumeH16() + consume(":") + lookAheadMatches("[^:]".toRegex()) } + consumeH16() } + consume("::") + consumeLs32() }
            ?: attemptTo { optionalStr { betweenStr(0, 5) { consumeH16() + consume(":") + lookAheadMatches("[^:]".toRegex()) } + consumeH16() } + consume("::") + consumeH16() }
            ?: let { optionalStr { betweenStr(0, 6) { consumeH16() + consume(":") + lookAheadMatches("[^:]".toRegex()) } + consumeH16() } + consume("::") }
    }

    fun consumeLs32(): String {
        return attemptTo { consumeH16() + consume(':') + consumeH16() }
            ?: consumeIpv4Address()
    }

    fun consumeH16(): String {
        return consumeMatching("[0-9a-fA-F]{1,4}".toRegex())
    }

    fun consumeIpv4Address(): String {
        return consumeDecOctet() + consume('.') + consumeDecOctet() + consume('.') + consumeDecOctet() + consume('.') + consumeDecOctet()
    }

    fun consumeDecOctet(): String {
        return consumeMatching("25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?".toRegex())
    }

    fun consumeIpvFuture(): String {
        return consume('v') +
                oneOrMoreStr { consumeHexDigit() } +
                consume('.') +
                oneOrMoreStr {
                    attemptTo { consumeUnreserved() }
                        ?: attemptTo { consumeSubDelims() }
                        ?: consume(':').toString()
                }
    }

    fun consumeUnreserved(): String {
        return attemptTo { consumeAlpha() }
            ?: attemptTo { consumeDigit() }
            ?: attemptTo { consume('-').toString() }
            ?: attemptTo { consume('.').toString() }
            ?: attemptTo { consume('_').toString() }
            ?: consume('~').toString()
    }

    fun consumePchar(): String {
        return attemptTo { consumeUnreserved() }
            ?: attemptTo { consumePctEncoded() }
            ?: attemptTo { consumeSubDelims() }
            ?: attemptTo { consume(':').toString() }
            ?: consume('@').toString()
    }

    fun consumePctEncoded(): String {
        return consume('%') + consumeHexDigit() + consumeHexDigit()
    }

    fun consumeSubDelims(): String {
        return consumeMatching("[!$&'()*+,;=]".toRegex())
    }

    fun consumeHexDigit(): String {
        return consumeMatching("[0-9a-fA-F]".toRegex())
    }

    fun lookAheadMatches(regex: Regex): String {
        val match = regex.find(contents, i)
        require(match != null && match.range.first == i) { "Expected ${regex.pattern} at position $i" }
        require(match.value.isNotEmpty()) { "Empty match at position $i" }
        return ""
    }
    fun zeroOrMoreStr(block: () -> String) = zeroOrMore(block).joinToString("")
    fun oneOrMoreStr(block: () -> String) = oneOrMore(block).joinToString("")
    fun optionalStr(block: () -> String) = optional(block)?.toString() ?: ""
    fun betweenStr(min: Int, max: Int, block: () -> String) = between(min, max, block).joinToString("")
    fun repeatedStr(n: Int, block: () -> String) = repeated(n, block).joinToString("")
}
