package com.wrmh.allmyfood.views.recipes

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.api.API
import com.wrmh.allmyfood.models.*
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
        val realPath = SelectedFilePath.getPath(context, path)

        val file = File(realPath)

        val requestFile =
            RequestBody.create(MediaType.parse("image/*"), file)

        val body =
            MultipartBody.Part.createFormData("recipeImage", file.name, requestFile)

        coroutineScope.launch {

            val createRecipeDeferred = API().createRecipeAsync(
                recipeToCreate.author,
                recipeToCreate.title,
                recipeToCreate.desc,
                recipeToCreate.steps!!,
                recipeToCreate.ingredients!!,
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

    fun updateRecipe(listToUpdate: ListModel, context: Context) {
        coroutineScope.launch {

            val listToSend = PostListModel(
                CurrentUser.username!!,
                listToUpdate
            )

            val updateListDeferred = API().updateListAsync(
                listToSend
            )

            try {
                val apiResponse = updateListDeferred.await()

                if (apiResponse.error)
                    throw java.lang.Exception()

                Toast.makeText(context, R.string.update_message, Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(context, R.string.error, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}


