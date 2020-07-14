package com.wrmh.allmyfood.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListModel(
    val _id: String,
    var name: String,
    val desc: String,
    @SerializedName("elements") var elements: List<ElementModel>
) : Parcelable