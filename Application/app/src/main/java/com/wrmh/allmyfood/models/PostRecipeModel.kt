package com.wrmh.allmyfood.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostRecipeModel(
    var author: String,
    var title: String,
    var desc: String,
    var recipeType: List<String>?,
    @SerializedName("steps") var steps: List<StepModel>?,
    @SerializedName("ingredients") var ingredients: List<IngredientModel>?,
    var privacy: Boolean,
    var recipeImage: String?
) : Parcelable