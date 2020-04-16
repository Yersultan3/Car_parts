package com.example.car_parts.models

import android.net.Uri
import android.os.Parcelable
import android.widget.Spinner
import kotlinx.android.parcel.Parcelize

@Parcelize

data class TireProduct(
    val id: String,
    val width: String,
    val profile: String,
    val diameter: String,
    val manufacturer: String,
    val seasonality: String,
    val condition: String,
    val image: String,
//    val image: List<ImagesDeatails>,
    val price: String,
    val addedBy: String
) : Parcelable

