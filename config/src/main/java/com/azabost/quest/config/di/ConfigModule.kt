package com.azabost.quest.config.di

import com.azabost.quest.config.AndroidBuildConfig
import com.azabost.quest.config.Config
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
abstract class ConfigModule {

    @Binds
    abstract fun bindConfig(androidBuildConfig: AndroidBuildConfig): Config
}
