package com.wrmh.allmyfood.views.lists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wrmh.allmyfood.api.API
import com.wrmh.allmyfood.models.CurrentUser
import com.wrmh.allmyfood.models.ElementModel
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

    private fun getUserLists() {
        coroutineScope.launch {
            val getListsDeferred = API().getUserLists(CurrentUser.username!!)

            try {
                val apiResponse = getListsDeferred.await()

                _lists.value = apiResponse.lists

                if(apiResponse.lists.isEmpty())
                    throw Exception()
            } catch (e: Exception) {
                _lists.value = arrayListOf(
                    ListModel(
                        "Agrega una lista para visualizar acá ...",
                        "",
                        ArrayList<ElementModel>()
                    )
                )
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