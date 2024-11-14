package com.martmists.multiplatform.logging

private external interface Console {
    // Supported by any browser since ~2012
    fun debug(vararg o: Any?)
    fun info(vararg o: Any?)
    fun warn(vararg o: Any?)
    fun error(vararg o: Any?)
    fun trace(vararg o: Any?)
}

private external val console: Console

open class JsLogger(private val name: String) : Logger {
    override fun debug(message: String) = console.debug("[$name]", message)
    override fun debug(message: String, error: Throwable) = console.debug("[$name]", message, "\n" + error.stackTraceToString())
    override fun info(message: String) = console.info("[$name]", message)
    override fun info(message: String, error: Throwable) = console.info("[$name]", message, "\n" + error.stackTraceToString())
    override fun warn(message: String) = console.warn("[$name]", message)
    override fun warn(message: String, error: Throwable) = console.warn("[$name]", message, "\n" + error.stackTraceToString())
    override fun error(message: String) = console.error("[$name]", message)
    override fun error(message: String, error: Throwable) = console.error("[$name]", message, "\n" + error.stackTraceToString())
    override fun trace(message: String) = console.trace("[$name]", message)
    override fun trace(message: String, error: Throwable) = console.trace("[$name]", message, "\n" + error.stackTraceToString())
}

actual fun makeLogger(name: String): Logger = JsLogger(name)
