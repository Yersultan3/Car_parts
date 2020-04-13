package com.example.car_parts.models

import android.os.Parcelable
import android.widget.Spinner
import kotlinx.android.parcel.Parcelize

@Parcelize

data class ImagesDeatails(
    val image0: String,
    val image1: String,
    val image2: String,
    val image3: String
) : Parcelable

