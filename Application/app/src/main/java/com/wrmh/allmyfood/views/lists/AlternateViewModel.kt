package com.wrmh.allmyfood.views.lists

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.api.API
import com.wrmh.allmyfood.models.CurrentUser
import com.wrmh.allmyfood.models.ListModel
import com.wrmh.allmyfood.models.PostListModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AlternateViewModel : ViewModel(){
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    lateinit var callback: () -> Unit

    fun createUserList(listToCreate: ListModel, context: Context){
        coroutineScope.launch {

            val listToSend = PostListModel(
                CurrentUser.username!!,
                listToCreate
            )

            val createListDeferred = API().createListAsync(
                listToSend
            )

            try {
                val apiResponse = createListDeferred.await()

                if(apiResponse.error)
                    throw java.lang.Exception()

                Toast.makeText(context, R.string.create_message, Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(context, R.string.error, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun updateUserList(listToUpdate: ListModel, context: Context){
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

                if(apiResponse.error)
                    throw java.lang.Exception()

                Toast.makeText(context, R.string.update_message, Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(context, R.string.error, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun deleteUserList(id: String, context: Context){
        coroutineScope.launch {
            val deleteListDeferred = API().deleteListAsync(
                CurrentUser.username!!,
                id
            )

            try {
                val apiResponse = deleteListDeferred.await()

                if(apiResponse.error)
                    throw java.lang.Exception()

                callback()
                Toast.makeText(context, "Lista eliminada ...", Toast.LENGTH_LONG).show()
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