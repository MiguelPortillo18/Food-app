package com.wrmh.allmyfood.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.wrmh.allmyfood.responses.ListResponse
import com.wrmh.allmyfood.responses.LoginResponse
import com.wrmh.allmyfood.responses.RecipeResponse
import com.wrmh.allmyfood.responses.RegisterResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://food-api-wrmh.herokuapp.com/"

interface API {
    @FormUrlEncoded
    @POST("user")
    fun createUserAsync(
        @Field("username") username: String,
        @Field("fullname") fullname: String,
        @Field("password") password: String,
        @Field("email") email: String
    ): Deferred<RegisterResponse>

    @GET("user")
    fun loginUserAsync(
        @Query("username") username: String,
        @Query("password") password: String
    ): Deferred<LoginResponse>

    @GET("recipe")
    fun userRecipesAsync(
        @Query("author") author: String
    ): Deferred<RecipeResponse>

    @GET("recipe")
    fun exploreRecipesAsync(): Deferred<RecipeResponse>

    @GET("list")
    fun getUserLists(
        @Query("username") username: String
    ): Deferred<ListResponse>

    companion object {
        operator fun invoke(): API {
            val requestInterceptor = Interceptor {
                val url = it.request()
                    .url()
                    .newBuilder()
                    .build()

                val request = it.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor it.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(90, TimeUnit.SECONDS)
                .readTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .addInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(API::class.java)
        }
    }
}