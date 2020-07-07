package com.wrmh.allmyfood.models

data class ListModel(
    val name: String,
    val desc: String,
    val elements: ArrayList<ElementModel>?
)