package com.wrmh.allmyfood.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecipeModel(
    val author: String,
    var title: String,
    val desc: String,
    val recipeType: List<String>?,
    val steps: List<StepModel>?,
    val ingredients: List<IngredientModel>?,
    val privacy: Boolean,
    val recipeImage: String?
) : Parcelable