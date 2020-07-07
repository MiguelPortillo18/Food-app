package com.wrmh.allmyfood.responses

import com.wrmh.allmyfood.models.ListModel

data class ListResponse (val error: Boolean, val message: String, val lists: ArrayList<ListModel>)