package com.wrmh.allmyfood.factory

import com.wrmh.allmyfood.views.recipes.DetailRecipeViewModel
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wrmh.allmyfood.models.RecipeModel
import com.wrmh.allmyfood.views.recipes.RecipesViewModel

class RecipesViewModelFactory(
    private val recipe: RecipeModel,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailRecipeViewModel::class.java)) {
            return DetailRecipeViewModel(recipe, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}