package com.example.allmyfood.models

import java.io.File
import java.time.Instant
import java.util.*

object CurrentUser {
    var username: String? = null
    var fullname: String? = null
    var loginTimeStamp: Date? = null

    fun onLoginSuccessful(username: String, fullname: String) {
        this.username = username
        this.fullname = fullname

        loginTimeStamp = Calendar.getInstance().time
    }
}