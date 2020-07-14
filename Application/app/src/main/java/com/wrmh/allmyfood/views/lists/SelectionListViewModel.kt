package com.wrmh.allmyfood.views.lists

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.api.API
import com.wrmh.allmyfood.models.CurrentUser
import com.wrmh.allmyfood.models.ListModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SelectionListViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _lists = MutableLiveData<List<ListModel>>()
    private val _navigateToSelectedProperty = MutableLiveData<ListModel>()

    val response: LiveData<List<ListModel>>
        get() = _lists

    val navigateToSelectedProperty: LiveData<ListModel>
        get() = _navigateToSelectedProperty

    lateinit var callback: () -> Unit

    init{
        getUserLists()
    }

    fun getUserLists() {
        coroutineScope.launch {
            val getListsDeferred = API().getUserListsAsync(CurrentUser.username!!)

            try {
                val apiResponse = getListsDeferred.await()

                _lists.value = apiResponse.lists

                if(apiResponse.lists.isEmpty())
                    throw Exception()
            } catch (e: Exception) {
                _lists.value = ArrayList()
            }
            finally {
                callback()
            }
        }
    }

    fun displayListDetail(list: ListModel){
        _navigateToSelectedProperty.value = list
    }

    fun displayListDetailCompleted(){
        _navigateToSelectedProperty.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}