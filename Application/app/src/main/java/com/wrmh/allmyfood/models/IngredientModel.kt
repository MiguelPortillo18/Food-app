package com.wrmh.allmyfood.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IngredientModel(val ingredient: String, val measure: String) : Parcelable