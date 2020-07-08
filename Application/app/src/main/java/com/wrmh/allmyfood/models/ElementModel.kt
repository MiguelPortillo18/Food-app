package com.wrmh.allmyfood.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ElementModel (var desc: String, val quantity: String) : Parcelable