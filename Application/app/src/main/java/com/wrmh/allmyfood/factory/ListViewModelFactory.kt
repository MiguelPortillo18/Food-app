package com.wrmh.allmyfood.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wrmh.allmyfood.models.ListModel
import com.wrmh.allmyfood.views.lists.MyListViewModel

class ListViewModelFactory(
    private val list: ListModel,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyListViewModel::class.java)) {
            return MyListViewModel(list, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}