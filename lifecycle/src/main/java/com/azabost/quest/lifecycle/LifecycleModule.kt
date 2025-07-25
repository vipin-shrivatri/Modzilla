package com.azabost.quest.lifecycle

import android.app.Application
import com.azabost.quest.share.SharingActivityHolder
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LifecycleModule {

    @Binds
    fun activityLifecycleCallbacks(activityLifecycleCallbacks: ActivityLifecycleCallbacks): Application.ActivityLifecycleCallbacks

    @Binds
    fun sharingActivityHolder(activityLifecycleCallbacks: ActivityLifecycleCallbacks): SharingActivityHolder
}