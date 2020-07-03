package com.wrmh.allmyfood.views.recipes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wrmh.allmyfood.api.API
import com.wrmh.allmyfood.models.RecipeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ExploreViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private var recipes = MutableLiveData<List<RecipeModel>>()

    val response: LiveData<List<RecipeModel>>
        get() = recipes

    init {
        getAllRecipes()
    }

    private fun getAllRecipes() {
        coroutineScope.launch {
            val getRecipesDeferred = API().exploreRecipesAsync()

            try {
                val apiResponse = getRecipesDeferred.await()
                recipes.value = apiResponse.recipes
            } catch (e: Exception) {
                recipes.value = ArrayList<RecipeModel>()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}