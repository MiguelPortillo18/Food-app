package com.wrmh.allmyfood.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecipeModel(
    var author: String,
    var title: String,
    var desc: String,
    var recipeType: List<String>?,
    var steps: List<StepModel>?,
    var ingredients: List<IngredientModel>?,
    var privacy: Boolean,
    var recipeImage: String?
) : Parcelable