package com.example.turtlewowcompanion.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {

    private const val EMULATOR_BASE_URL  = "http://10.0.2.2:8084/"
    // Con USB + "adb reverse tcp:8084 tcp:8084" el movil usa 127.0.0.1 como si fuera localhost del PC
    private const val DEVICE_BASE_URL    = "http://127.0.0.1:8084/"

    // true = emulador | false = movil fisico con adb reverse
    private const val USE_EMULATOR = false

    private val baseUrl: String = if (USE_EMULATOR) EMULATOR_BASE_URL else DEVICE_BASE_URL

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(8, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .callTimeout(12, TimeUnit.SECONDS)
        .build()

    val api: TurtleWowApi by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TurtleWowApi::class.java)
    }
}
