package com.azabost.quest.posts.di

import com.azabost.quest.posts.PostsRepository
import com.azabost.quest.posts.remote.PostsService
import com.azabost.quest.posts.remote.RemotePostsCache
import com.azabost.quest.posts.remote.RemotePostsRepository
import com.azabost.quest.time.NowProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton
import kotlin.time.Duration.Companion.hours

@Module
@InstallIn(SingletonComponent::class)
interface PostsModule {

    @Binds
    fun postsRepository(repository: RemotePostsRepository): PostsRepository

    companion object {
        @Provides
        @Singleton
        fun postsService(okHttpClient: OkHttpClient, converterFactory: Converter.Factory): PostsService =
            Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(converterFactory)
                .build()
                .create(PostsService::class.java)

        @Provides
        @Singleton
        fun remotePostsCache(nowProvider: NowProvider) =
            RemotePostsCache(
                cacheExpiryTimeMillis = 1.hours.inWholeMilliseconds,
                nowProvider = nowProvider,
            )
    }
}