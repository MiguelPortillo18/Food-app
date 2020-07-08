package com.wrmh.allmyfood.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListModel(
    val name: String,
    val desc: String,
    val elements: List<ElementModel>
) : Parcelable