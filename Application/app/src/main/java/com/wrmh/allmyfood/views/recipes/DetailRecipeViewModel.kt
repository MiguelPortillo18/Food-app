package com.wrmh.allmyfood.views.recipes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wrmh.allmyfood.models.RecipeModel

class DetailRecipeViewModel(
    recipe: RecipeModel,
    app: Application
) : AndroidViewModel(app){

    private val _selectedProperty = MutableLiveData<RecipeModel>()

    val selectedProperty: LiveData<RecipeModel>
        get() = _selectedProperty

    init {
        _selectedProperty.value = recipe
    }
}