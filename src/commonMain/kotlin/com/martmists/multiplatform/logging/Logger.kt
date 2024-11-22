package com.martmists.multiplatform.logging

import kotlin.reflect.typeOf

interface Logger {
    fun debug(message: String)
    fun debug(message: String, error: Throwable)
    fun info(message: String)
    fun info(message: String, error: Throwable)
    fun warn(message: String)
    fun warn(message: String, error: Throwable)
    fun error(message: String)
    fun error(message: String, error: Throwable)
    fun trace(message: String)
    fun trace(message: String, error: Throwable)

    companion object : Logger by makeLogger("Logger")
}

expect fun makeLogger(name: String): Logger
