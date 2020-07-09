package com.wrmh.allmyfood.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StepModel(val order: Int, var step: String) : Parcelable