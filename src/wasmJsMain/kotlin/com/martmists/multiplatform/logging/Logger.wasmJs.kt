package com.martmists.multiplatform.logging

private external interface Console {
    // Supported by any browser since ~2012
    fun debug(vararg o: JsAny?)
    fun info(vararg o: JsAny?)
    fun warn(vararg o: JsAny?)
    fun error(vararg o: JsAny?)
    fun trace(vararg o: JsAny?)
}

private val consoleReference: JsReference<Console> = js("console")

open class WasmLogger(private val name: String) : Logger {
    private val console: Console = consoleReference.get()

    override fun debug(message: String) = console.debug("[$name]".toJsString(), message.toJsString())
    override fun debug(message: String, error: Throwable) = console.debug("[$name]".toJsString(), message.toJsString(), ("\n" + error.stackTraceToString()).toJsString())
    override fun info(message: String) = console.info("[$name]".toJsString(), message.toJsString())
    override fun info(message: String, error: Throwable) = console.info("[$name]".toJsString(), message.toJsString(), ("\n" + error.stackTraceToString()).toJsString())
    override fun warn(message: String) = console.warn("[$name]".toJsString(), message.toJsString())
    override fun warn(message: String, error: Throwable) = console.warn("[$name]".toJsString(), message.toJsString(), ("\n" + error.stackTraceToString()).toJsString())
    override fun error(message: String) = console.error("[$name]".toJsString(), message.toJsString())
    override fun error(message: String, error: Throwable) = console.error("[$name]".toJsString(), message.toJsString(), ("\n" + error.stackTraceToString()).toJsString())
    override fun trace(message: String) = console.trace("[$name]".toJsString(), message.toJsString())
    override fun trace(message: String, error: Throwable) = console.trace("[$name]".toJsString(), message.toJsString(), ("\n" + error.stackTraceToString()).toJsString())
}

actual fun makeLogger(name: String): Logger = WasmLogger(name)
