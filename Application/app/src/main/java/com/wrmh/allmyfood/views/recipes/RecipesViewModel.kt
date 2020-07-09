package com.wrmh.allmyfood.views.recipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wrmh.allmyfood.api.API
import com.wrmh.allmyfood.models.CurrentUser
import com.wrmh.allmyfood.models.ListModel
import com.wrmh.allmyfood.models.RecipeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RecipesViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var _recipes = MutableLiveData<List<RecipeModel>>()
    private var _navigateToSelectedProperty = MutableLiveData<RecipeModel>()

    val response: LiveData<List<RecipeModel>>
        get() = _recipes

    val navigateToSelectedProperty: LiveData<RecipeModel>
        get() = _navigateToSelectedProperty

    lateinit var callbackFunction: () -> Unit

    fun redirection(b: Boolean){
        if(b)
            getAllRecipes()
        else
            getAllUserRecipes()
    }

    private fun getAllRecipes() {
        coroutineScope.launch {
            val getRecipesDeferred = API().exploreRecipesAsync()

            try {
                val apiResponse = getRecipesDeferred.await()
                _recipes.value = apiResponse.recipes

                if (apiResponse.recipes.isEmpty())
                    throw java.lang.Exception()
            } catch (e: Exception) {
                _recipes.value = ArrayList()
            } finally {
                callbackFunction()
            }
        }
    }

    private fun getAllUserRecipes() {
        coroutineScope.launch {
            val getUserRecipesDeferred = API().userRecipesAsync(CurrentUser.username!!)

            try {
                val apiResponse = getUserRecipesDeferred.await()
                _recipes.value = apiResponse.recipes

                if(apiResponse.recipes.isEmpty())
                    throw java.lang.Exception()

            } catch (e: Exception) {
                _recipes.value = ArrayList()
            }
            finally {
                callbackFunction()
            }
        }
    }

    fun displayRecipeDetail(recipe: RecipeModel){
        _navigateToSelectedProperty.value = recipe
    }

    fun displayRecipeDetailCompleted(){
        _navigateToSelectedProperty.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}