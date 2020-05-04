package com.example.car_parts.models
import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
//@Entity(tableName = "users")
data class User(
    val name: String,
    val phoneNumber: String,
    val address: ArrayList<Double>
//    val password: String
) : Parcelable