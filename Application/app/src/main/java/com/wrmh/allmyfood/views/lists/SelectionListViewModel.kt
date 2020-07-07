package com.wrmh.allmyfood.views.lists

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.api.API
import com.wrmh.allmyfood.models.CurrentUser
import com.wrmh.allmyfood.models.ListModel
import com.wrmh.allmyfood.models.RecipeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SelectionListViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val lists = MutableLiveData<List<ListModel>>()

    val response: LiveData<List<ListModel>>
        get() = lists

    lateinit var callback: () -> Unit

    init{
        getUserLists()
    }

    fun getUserLists() {
        coroutineScope.launch {
            val getListsDeferred = API().getUserLists(CurrentUser.username!!)

            try {
                val apiResponse = getListsDeferred.await()

                lists.value = apiResponse.lists

                if(apiResponse.lists.isEmpty())
                    throw Exception()
            } catch (e: Exception) {
                lists.value = arrayListOf(
                    ListModel(
                        "Agregar una lista para visualizar acá ...",
                        "",
                        null
                    )
                )
            }
            finally {
                callback()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}