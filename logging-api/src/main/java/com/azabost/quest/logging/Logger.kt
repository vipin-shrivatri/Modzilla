package com.azabost.quest.logging

import com.azabost.quest.analytics.AnalyticsEvent
import kotlin.reflect.KClass

interface Logger {
    val name: String

    fun debug(message: String)
    fun info(message: String)
    fun error(message: String, throwable: Throwable? = null)

    interface Factory {
        fun create(name: String): Logger
    }
}

fun Logger.event(analyticsEvent: AnalyticsEvent) = info("event: ${analyticsEvent.key}")

fun Logger.Factory.create(kClass: KClass<*>): Logger = create(kClass.java.simpleName)