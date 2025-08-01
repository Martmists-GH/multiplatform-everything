package com.martmists.multiplatform.parsing.core

import kotlin.text.iterator

abstract class Lexer(protected val contents: String) : Iterator<Char> {
    protected var i = 0
    private val stack = mutableListOf<Int>()

    override fun hasNext() = i <= contents.lastIndex

    override fun next() = contents[i++]

    fun loc(): Loc {
        val line = contents.substring(0, i).count { it == '\n' }
        val col = i - contents.substring(0, i).lastIndexOf('\n') - 1
        return Loc(line, col)
    }

    protected fun peek(): Char? = if (i <= contents.lastIndex) contents[i] else null
    protected fun peek2(): Char? = if (i < contents.lastIndex) contents[i + 1] else null
    protected fun peek3(): Char? = if (i < contents.lastIndex - 1) contents[i + 2] else null

    protected fun save() {
        stack.add(i)
    }

    protected fun reset() {
        i = stack.removeLast()
    }

    protected fun discard() {
        stack.removeLast()
    }

    protected open fun consumeIgnored() {

    }

    protected fun consume(char: Char, skip: Boolean = true): Char {
        require(peek() != null) { "EOF reached" }
        require(next() == char) { "Expected '$char' at position $i" }
        if (skip) {
            consumeIgnored()
        }
        return char
    }

    protected fun consume(string: String, skip: Boolean = true): String {
        require(peek() != null) { "EOF reached" }
        for (c in string) {
            require(next() == c) { "Expected '$c' at position $i" }
        }
        if (skip) {
            consumeIgnored()
        }
        return string
    }

    protected fun consumeMatching(regex: Regex, skip: Boolean = true): String {
        val match = regex.find(contents, i)
        require(match != null && match.range.first == i) { "Expected ${regex.pattern} at position $i" }
        require(match.value.isNotEmpty()) { "Empty match at position $i" }
        i += match.value.length
        if (skip) {
            consumeIgnored()
        }
        return match.value
    }

    fun consumeEOF(): String {
        require(!hasNext()) { "Expected EOF at position $i" }
        return ""
    }

    protected fun <T> zeroOrMore(block: () -> T) = between(0..Int.MAX_VALUE, block)
    protected fun <T> oneOrMore(block: () -> T) = between(1..Int.MAX_VALUE, block)
    protected fun <T> repeated(n: Int, block: () -> T) = between(n..n, block)
    protected fun <T> optional(block: () -> T) = between(0..1, block).firstOrNull()

    protected fun <T> between(min: Int, max: Int, block: () -> T) = between(min..max, block)
    protected fun <T> between(range: IntRange, block: () -> T): List<T> {
        val found = mutableListOf<T>()

        while (hasNext() && found.size < range.endInclusive) {
            attemptTo {
                block()
            }?.let {
                found.add(it)
            } ?: break
        }

        require(found.size in range) { "Expected between $range, got ${found.size}" }

        return found
    }

    protected fun <T> attemptTo(block: () -> T): T? {
        save()
        try {
            return block().also { discard() }
        } catch (e: IllegalArgumentException) {
            reset()
            return null
        }
    }
}
