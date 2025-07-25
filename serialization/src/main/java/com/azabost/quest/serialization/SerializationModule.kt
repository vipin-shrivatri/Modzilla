package com.azabost.quest.serialization

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SerializationModule {

    @Provides
    @Singleton
    fun json(): Json =
        Json(from = Json.Default) {
            ignoreUnknownKeys = true
        }

    @Provides
    @Singleton
    fun converterFactory(json: Json) =
        json.asConverterFactory("application/json".toMediaType())

}