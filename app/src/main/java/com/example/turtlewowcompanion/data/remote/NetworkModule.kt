package com.example.turtlewowcompanion.data.remote

import com.example.turtlewowcompanion.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {

    // Base URL configurada por buildType:
    //   debug   → http://10.0.2.2:8084/  (emulador AVD apuntando al backend local)
    //   release → URL pública (Render)
    // Con USB + "adb reverse tcp:8084 tcp:8084" un móvil físico puede usar la URL del emulador.
    private val baseUrl: String = BuildConfig.BASE_URL

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC
            else HttpLoggingInterceptor.Level.NONE
        })
        // Timeouts amplios: la primera petición tras un cold start del backend (Render free
        // tier o arranque local) puede tardar bastantes segundos. Cubrimos hasta 90-120s
        // para que el cliente no aborte la petición y el usuario vea el dato cuando llega.
        .connectTimeout(90, TimeUnit.SECONDS)
        .readTimeout(90, TimeUnit.SECONDS)
        .writeTimeout(90, TimeUnit.SECONDS)
        .callTimeout(120, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
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
