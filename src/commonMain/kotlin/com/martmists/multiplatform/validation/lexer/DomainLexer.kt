package com.martmists.multiplatform.validation.lexer

import com.martmists.multiplatform.parsing.core.ABNFLexer

class DomainLexer(contents: String) : ABNFLexer(contents) {
    fun consumeDomain(): String {
        return if (contents == " ") {
            " "
        } else {
            consumeSubdomain().also {
                consumeEOF()
            }
        }
    }

    fun consumeSubdomain(): String {
        return consumeLabel() + zeroOrMoreStr {
            consume('.') + consumeLabel()
        }
    }

    fun consumeLabel(): String {
        val label = StringBuilder()
        label.append(consumeLetter())
        while (hasNext()) {
            val next2 = peek2()
            if (next2 == null || next2 == '.') {
                break
            }

            label.append(consumeLetDigHyp())
        }
        label.append(consumeLetDig())
        require(label.length <= 63) { "Label too long at position $i (max 63, got ${label.length}" }
        return label.toString()
    }

    fun consumeLetDigHyp(): String {
        return attemptTo { consumeLetDig() }
            ?: consume('-').toString()
    }

    fun consumeLetDig(): String {
        return attemptTo { consumeLetter() }
            ?: consumeDigit()
    }

    fun consumeLetter(): String {
        return consumeMatching("[a-zA-Z]".toRegex())
    }

    fun zeroOrMoreStr(block: () -> String) = zeroOrMore { block() }.joinToString("")
}
