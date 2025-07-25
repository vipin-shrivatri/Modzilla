package com.azabost.quest.config

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
abstract class ConfigModule {

    @Binds
    abstract fun config(config: AndroidBuildConfig): Config

    companion object {
        @Provides
        @Named("isDebug")
        fun provideIsDebug(): Boolean = BuildConfig.DEBUG
    }
}