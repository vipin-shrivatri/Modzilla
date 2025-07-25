package com.azabost.quest.share

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ShareModule {

    @Binds
    fun shareSender(shareSenderImpl: ShareSenderImpl): ShareSender
}