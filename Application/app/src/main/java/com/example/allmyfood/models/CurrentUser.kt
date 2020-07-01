package com.example.allmyfood.models

object CurrentUser {
    var username: String? = null
    var fullname: String? = null

    fun onLoginSuccessful(username: String, fullname: String){
        this.username = username
        this.fullname = fullname
    }
}