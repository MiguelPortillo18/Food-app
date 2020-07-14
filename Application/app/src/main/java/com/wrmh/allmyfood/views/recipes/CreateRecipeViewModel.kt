package com.wrmh.allmyfood.views.recipes

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.api.API
import com.wrmh.allmyfood.models.RecipeModel
import com.wrmh.allmyfood.models.SelectedFilePath
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class CreateRecipeViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun createRecipe(path: Uri, recipeToCreate: RecipeModel, context: Context) {
        lateinit var body: MultipartBody.Part

        if (path.toString().isEmpty()) {
            body =
                MultipartBody.Part.createFormData("recipeImage", "")
        } else {
            val realPath = SelectedFilePath.getPath(context, path)

            val file = File(realPath!!)

            val requestFile =
                RequestBody.create(MediaType.parse("image/*"), file)

            body =
                MultipartBody.Part.createFormData("recipeImage", file.name, requestFile)

        }

        val ingredients = ArrayList<MultipartBody.Part>()
        val steps = ArrayList<MultipartBody.Part>()

        recipeToCreate.ingredients?.forEach {
            ingredients.add(
                MultipartBody.Part.createFormData("ingredients", it)
            )
        }

        recipeToCreate.steps?.forEach {
            steps.add(
                MultipartBody.Part.createFormData("steps", it)
            )
        }

        coroutineScope.launch {

            val createRecipeDeferred = API().createRecipeAsync(
                recipeToCreate.author,
                recipeToCreate.title,
                recipeToCreate.desc,
                ingredients,
                steps,
                recipeToCreate.privacy,
                body
            )

            try {
                val apiResponse = createRecipeDeferred.await()

                if (apiResponse.error)
                    throw java.lang.Exception()

                Toast.makeText(context, R.string.create_message, Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(
                    context, R.string.error,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}


