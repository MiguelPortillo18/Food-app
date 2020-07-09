package com.wrmh.allmyfood.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.wrmh.allmyfood.models.*
import com.wrmh.allmyfood.responses.*
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
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
    fun loginUserWithNameAsync(
        @Query("username") username: String,
        @Query("password") password: String
    ): Deferred<LoginResponse>

    @GET("user")
    fun loginUserWithEmailAsync(
        @Query("email") email: String,
        @Query("password") password: String
    ): Deferred<LoginResponse>

    @GET("recipe")
    fun userRecipesAsync(
        @Query("author") author: String
    ): Deferred<RecipeResponse>

    @GET("recipe")
    fun exploreRecipesAsync(): Deferred<RecipeResponse>

    @Multipart
    @POST("recipe")
    fun createRecipeAsync(
        @Part("author") author: RequestBody,
        @Part("title") title: RequestBody,
        @Part("desc") desc: RequestBody,
        @Part("steps") steps: RequestBody,
        @Part("ingredients") ingredients: RequestBody,
        @Part("privacy") privacy: RequestBody,
        @Part recipeImage: MultipartBody.Part
    ): Deferred<DefaultResponse>

    @GET("list")
    fun getUserListsAsync(
        @Query("username") username: String
    ): Deferred<ListResponse>

    @POST("list")
    fun createListAsync(
        @Body listData: PostListModel
    ): Deferred<DefaultResponse>

    @PUT("list")
    fun updateListAsync(
        @Body listData: PostListModel
    ): Deferred<DefaultResponse>

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