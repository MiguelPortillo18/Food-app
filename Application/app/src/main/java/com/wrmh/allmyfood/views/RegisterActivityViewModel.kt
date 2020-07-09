package com.wrmh.allmyfood.views

import android.content.Context
import android.net.Uri
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.api.API
import com.wrmh.allmyfood.models.CurrentUser
import com.wrmh.allmyfood.models.SelectedFilePath
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class RegisterActivityViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    lateinit var callback: () -> Unit

    fun createUser(
        path: Uri, username: String, password: String,
        fullname: String, email: String, context: Context,
        spiner: ProgressBar
    ) {
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
                MultipartBody.Part.createFormData("userImage", file.name, requestFile)

        }

        coroutineScope.launch {

            spiner.visibility = View.VISIBLE
            val createUserDeferred = API().createUserAsync(
                username,
                fullname,
                password,
                email,
                "",
                body
            )

            try {
                val apiResponse = createUserDeferred.await()

                if (apiResponse.error)
                    throw java.lang.Exception()

                CurrentUser.onLoginSuccessful(username, fullname, apiResponse.userImage)

                Toast.makeText(context, R.string.create_message, Toast.LENGTH_LONG).show()

                spiner.visibility = View.INVISIBLE
                callback()
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


