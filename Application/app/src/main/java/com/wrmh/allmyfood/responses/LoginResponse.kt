package com.wrmh.allmyfood.responses

data class LoginResponse(
    val error: Boolean,
    val username: String,
    val fullname: String,
    val userImage: String
)