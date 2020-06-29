package com.example.allmyfood

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://localhost:3500/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor{
            val original = it.request()

            val requestBuilder = original.newBuilder()
                .addHeader("Authorization", "")
                .method(original.method(), original.body())

            val request = requestBuilder.build()

            it.proceed(request)
        }.build()

    val instance: API by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(API::class.java)
    }
}