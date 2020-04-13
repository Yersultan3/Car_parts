package com.example.car_parts.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.car_parts.repositories.UserApiRepository

class UserViewModelFactory (private val dataSource: UserApiRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)){
            return UserViewModel(dataSource) as T
        }
        throw IllegalAccessException("Unknown ViewModel class")
    }
}