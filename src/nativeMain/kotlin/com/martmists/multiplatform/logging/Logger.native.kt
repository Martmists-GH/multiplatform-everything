package com.martmists.multiplatform.logging

import kotlinx.cinterop.ExperimentalForeignApi
import platform.posix.*

@OptIn(ExperimentalForeignApi::class)
class NativeLogger(private val name: String) : Logger {
    private fun stdout(message: String) {
        fprintf(stdout, "[$name] $message\n")
        fflush(stdout)
    }

    private fun stderr(message: String) {
        fprintf(stderr, "[$name] $message\n")
        fflush(stderr)
    }

    override fun debug(message: String) = stdout("DEBUG: $message")
    override fun debug(message: String, error: Throwable) {
        debug(message)
        debug(error.stackTraceToString())
    }
    override fun info(message: String) = stdout("INFO: $message")
    override fun info(message: String, error: Throwable) {
        info(message)
        info(error.stackTraceToString())
    }
    override fun warn(message: String) = stderr("WARN: $message")
    override fun warn(message: String, error: Throwable) {
        warn(message)
        warn(error.stackTraceToString())
    }
    override fun error(message: String) = stderr("ERROR: $message")
    override fun error(message: String, error: Throwable) {
        error(message)
        error(error.stackTraceToString())
    }
    override fun trace(message: String) = stdout("TRACE: $message")
    override fun trace(message: String, error: Throwable) {
        trace(message)
        trace(error.stackTraceToString())
    }
}

actual fun makeLogger(name: String): Logger = NativeLogger(name)
