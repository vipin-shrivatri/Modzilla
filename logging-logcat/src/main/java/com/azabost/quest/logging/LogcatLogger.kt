package com.azabost.quest.logging

import android.util.Log
import com.azabost.quest.config.Config
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Inject

class LogcatLogger(
    override val name: String,
    private val config: Config,
) : Logger {

    class Factory @Inject constructor(
        private val config: Config
    ) : Logger.Factory {
        override fun create(name: String): Logger = LogcatLogger(name, config)
    }

    override fun debug(message: String) {
        if (!config.isDebug) return
        Log.d(name, message)
    }

    override fun info(message: String) {
        Log.i(name, message)
    }

    override fun error(message: String, throwable: Throwable?) {
        Log.e(name, message, throwable)
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface LogcatModule {
    @Binds
    @IntoSet
    fun logcatLoggerFactory(logcatLoggerFactory: LogcatLogger.Factory): Logger.Factory
}