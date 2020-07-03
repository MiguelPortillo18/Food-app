package com.wrmh.allmyfood.models

data class RecipeModel(
    val author: String,
    val title: String,
    val desc: String,
    val recipeType: List<String>,
    val steps: List<StepModel>,
    val ingredients: List<IngredientModel>,
    val privacy: Boolean,
    val recipeImage: String
)