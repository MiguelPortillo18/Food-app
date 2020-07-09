package com.wrmh.allmyfood.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecipeModel(
    var id: String?,
    var author: String,
    var title: String,
    var desc: String,
    var recipeType: List<String>?,
    var steps: List<String>?,
    var ingredients: List<String>?,
    var privacy: Boolean,
    var recipeImage: String?
) : Parcelable