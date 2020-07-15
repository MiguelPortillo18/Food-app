package com.wrmh.allmyfood.views.recipes

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.api.API
import com.wrmh.allmyfood.models.RecipeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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