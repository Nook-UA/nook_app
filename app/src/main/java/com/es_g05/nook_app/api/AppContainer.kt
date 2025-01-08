package com.es_g05.nook_app.api

import com.es_g05.nook_app.repositories.NetworkNookParksRepository
import com.es_g05.nook_app.repositories.NookParksRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

interface AppContainer {
    val nookParksRepository: NookParksRepository
}

class DefaultAppContainer : AppContainer {

    private val baseUrl: String = "http://192.168.93.149:8000/api/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: NookApiService by lazy {
        retrofit.create(NookApiService::class.java)
    }

    override val nookParksRepository: NookParksRepository by lazy {
        NetworkNookParksRepository(retrofitService)
    }
}