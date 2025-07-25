package com.azabost.quest.analytics

import android.os.Bundle
import com.azabost.quest.config.Config
import com.azabost.quest.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Analytics @Inject constructor(
    private val config: Config,
    loggerFactory: Logger.Factory
) {

    private val logger = loggerFactory.create("Analytics")
    fun logEvent(event: AnalyticsEvent, metadata: Map<String, Any?>? = null) {
        if (config.isDebug) {
            // Don't report analytics events when debugging
            logger.debug("Event: ${event.key}, metadata: $metadata")
        } else {
            val params = bundleOf(metadata)
            PseudoFirebaseAnalytics.logEvent(event.key, params)
        }
    }

    private fun bundleOf(metadata: Map<String, Any?>?): Bundle? =
        metadata?.let { map ->
            Bundle().apply {
                map.forEach { (key, value) ->
                    when (value) {
                        is String -> putString(key, value)
                        is Int -> putInt(key, value)
                        is Boolean -> putBoolean(key, value)
                        else -> putString(key, value?.toString())
                    }
                }
            }
        }
}

private object PseudoFirebaseAnalytics {
    fun logEvent(name: String, params: Bundle? = null) {
        // only pretending to be the real Firebase Analytics
        // this would normally be a 3rd party library
    }
}