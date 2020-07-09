package com.wrmh.allmyfood.models

import com.google.gson.annotations.SerializedName

data class PostListModel(val username: String,
                         @SerializedName("listData") val listData: ListModel)