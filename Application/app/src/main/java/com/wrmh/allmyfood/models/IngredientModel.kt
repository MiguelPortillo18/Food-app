package com.wrmh.allmyfood.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IngredientModel(var ingredient: String, val measure: String) : Parcelable