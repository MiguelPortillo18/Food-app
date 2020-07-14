package com.wrmh.allmyfood.responses

import com.wrmh.allmyfood.models.RecipeModel

data class RecipeResponse(
    val error: Boolean,
    val message: String,
    val recipes: List<RecipeModel>
)