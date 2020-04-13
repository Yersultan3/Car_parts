package com.example.car_parts.`interface`

import android.content.Context
import android.net.ConnectivityManager

object NetworkManager {
    fun isNetworkAvailable(context: Context):Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.getActiveNetworkInfo()
        if (networkInfo != null && networkInfo.isConnected())
        {
            return true
        }
        else
        {
            return false
        }
    }
}