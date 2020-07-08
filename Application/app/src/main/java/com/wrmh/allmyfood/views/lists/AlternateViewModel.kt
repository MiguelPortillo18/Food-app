package com.wrmh.allmyfood.views.lists

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.api.API
import com.wrmh.allmyfood.models.CurrentUser
import com.wrmh.allmyfood.models.ListModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AlternateViewModel : ViewModel(){
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun createUserList(listToCreate: ListModel, context: Context){
        coroutineScope.launch {
            val createListDeferred = API().createList(
                CurrentUser.username!!,
                listToCreate
            )

            try {
                val apiResponse = createListDeferred.await()

                if(apiResponse.error)
                    throw java.lang.Exception()
            } catch (e: Exception) {
                Toast.makeText(context, R.string.error, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun updateUserList(listToUpdate: ListModel, id: String, context: Context){

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}