package com.example.allmyfood.api

import com.example.allmyfood.models.DefaultResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface API {
    @FormUrlEncoded
    @POST("user")
    fun createUser(
        @Field("username") username:String,
        @Field("fullname") fullname:String,
        @Field("password") password:String,
        @Field("email") email:String
    ): Call<DefaultResponse>
}