package com.wrmh.allmyfood.views.lists

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.api.API
import com.wrmh.allmyfood.models.CurrentUser
import com.wrmh.allmyfood.models.ListModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyListViewModel(
    list: ListModel,
    app: Application
) : AndroidViewModel(app){
    private val _selectedProperty = MutableLiveData<ListModel>()

    val selectedProperty: LiveData<ListModel>
        get() = _selectedProperty

    init {
        _selectedProperty.value = list
    }
}