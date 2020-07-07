package com.wrmh.allmyfood.views.recipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wrmh.allmyfood.api.API
import com.wrmh.allmyfood.models.CurrentUser
import com.wrmh.allmyfood.models.RecipeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyRecipesViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private var recipes = MutableLiveData<List<RecipeModel>>()

    lateinit var callbackFunction: () -> Unit

    val response: LiveData<List<RecipeModel>>
        get() = recipes

    init {
        getAllUserRecipes()
    }

    private fun getAllUserRecipes() {
        coroutineScope.launch {
            val getUserRecipesDeferred = API().userRecipesAsync(CurrentUser.username!!)

            try {
                val apiResponse = getUserRecipesDeferred.await()
                recipes.value = apiResponse.recipes

                if(apiResponse.recipes.isEmpty())
                    throw java.lang.Exception()

            } catch (e: Exception) {
                recipes.value = arrayListOf(
                    RecipeModel(
                        "",
                        "Aun no existen recetas",
                        "Agrega una desde el menú (pública) para que puedas visualizarla acá",
                        null,
                        null,
                        null,
                        false,
                        "INF"
                    )
                )
            }
            finally {
                callbackFunction()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}