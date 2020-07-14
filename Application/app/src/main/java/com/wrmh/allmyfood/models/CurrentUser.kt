package com.wrmh.allmyfood.models

import java.util.*

object CurrentUser {
    var username: String? = null
    var fullname: String? = null
    var userImage: String? = null
    var loginTimeStamp: Date? = null

    fun onLoginSuccessful(username: String, fullname: String, userImage: String) {
        this.username = username
        this.fullname = fullname
        this.userImage = userImage

        loginTimeStamp = Calendar.getInstance().time
    }
}