package com.azabost.quest.logging

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

class CompositeLogger(
    override val name: String,
    private val loggers: Collection<Logger>
) : Logger {

    @Singleton
    class Factory @Inject constructor(
        private val factories: Set<@JvmSuppressWildcards Logger.Factory>,
    ) : Logger.Factory {

        override fun create(name: String): Logger =
            CompositeLogger(
                name = name,
                loggers = factories.map { it.create(name) }
            )
    }

    override fun debug(message: String) {
        loggers.forEach { it.debug(message) }
    }

    override fun info(message: String) {
        loggers.forEach { it.info(message) }
    }

    override fun error(message: String, throwable: Throwable?) {
        loggers.forEach { it.error(message, throwable) }
    }
}


@Module
@InstallIn(SingletonComponent::class)
interface LoggingModule {
    @Binds
    fun factory(compositeLoggerFactory: CompositeLogger.Factory): Logger.Factory
}