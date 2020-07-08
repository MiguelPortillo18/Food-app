package com.wrmh.allmyfood.views.lists

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wrmh.allmyfood.models.ListModel

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