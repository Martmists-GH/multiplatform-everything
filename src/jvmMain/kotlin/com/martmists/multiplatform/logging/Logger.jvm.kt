package com.martmists.multiplatform.logging

import org.slf4j.LoggerFactory

open class JvmLogger(name: String) : Logger {
    private val impl = LoggerFactory.getLogger(name)

    override fun debug(message: String) = impl.debug(message)
    override fun debug(message: String, error: Throwable) = impl.debug(message, error)
    override fun info(message: String) = impl.info(message)
    override fun info(message: String, error: Throwable) = impl.info(message, error)
    override fun warn(message: String) = impl.warn(message)
    override fun warn(message: String, error: Throwable) = impl.warn(message, error)
    override fun error(message: String) = impl.error(message)
    override fun error(message: String, error: Throwable) = impl.error(message, error)
    override fun trace(message: String) = impl.trace(message)
    override fun trace(message: String, error: Throwable) = impl.trace(message, error)
}

actual fun makeLogger(name: String): Logger = JvmLogger(name)
inline fun <reified T> makeLogger(): Logger = makeLogger(T::class.qualifiedName ?: error("Unable to create logger for anonymous classes"))
