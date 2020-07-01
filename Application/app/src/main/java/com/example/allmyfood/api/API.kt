package com.example.allmyfood.api

import com.example.allmyfood.models.DefaultResponse
import com.example.allmyfood.models.LoginResponse
import retrofit2.Call
import retrofit2.http.*

interface API {
    @FormUrlEncoded
    @POST("user")
    fun createUser(
        @Field("username") username:String,
        @Field("fullname") fullname:String,
        @Field("password") password:String,
        @Field("email") email:String
    ): Call<DefaultResponse>

    @GET("user")
    fun loginUser(
        @Query("username") username: String,
        @Query("password") password: String
    ): Call<LoginResponse>
}