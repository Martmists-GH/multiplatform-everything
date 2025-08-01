package com.martmists.multiplatform.parsing.core

abstract class ABNFLexer(contents: String) : Lexer(contents) {
    fun consumeAlpha(): String {
        return consumeMatching("[a-zA-Z]".toRegex())
    }

    fun consumeBit(): String {
        return consumeMatching("[01]".toRegex())
    }

    fun consumeChar(): String {
        return consumeMatching("[\\x01-\\x7F]".toRegex())
    }

    fun consumeCr(): String {
        return consume('\r').toString()
    }

    fun consumeCrlf(): String {
        return consume("\r\n")
    }

    fun consumeCtl(): String {
        return consumeMatching("[\\x00-\\x1F\\x7F]".toRegex())
    }

    fun consumeDigit(): String {
        return consumeMatching("[0-9]".toRegex())
    }

    fun consumeDquote(): String {
        return consume('"').toString()
    }

    fun consumeHexdig(): String {
        return consumeMatching("[0-9a-fA-F]".toRegex())
    }

    fun consumeHtab(): String {
        return consume('\t').toString()
    }

    fun consumeLf(): String {
        return consume('\n').toString()
    }

    fun consumeLwsp(): String {
        return zeroOrMore {
            attemptTo { consumeWsp() }
                ?: (consumeCrlf() + consumeWsp())
        }.joinToString("")
    }

    fun consumeOctet(): String {
        return consumeMatching("[\\x00-\\xFF]".toRegex())
    }

    fun consumeSp(): String {
        return consume(' ').toString()
    }

    fun consumeVchar(): String {
        return consumeMatching("[\\x21-\\x7E]".toRegex())
    }

    fun consumeWsp(): String {
        return consumeMatching("[ \t]".toRegex())
    }
}
