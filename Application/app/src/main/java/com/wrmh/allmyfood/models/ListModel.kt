package com.wrmh.allmyfood.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListModel(
    val _id: String,
    val name: String,
    val desc: String,
    var elements: List<ElementModel>
) : Parcelable