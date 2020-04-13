package com.example.car_parts.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.car_parts.repositories.TireProductApiRepository
import com.example.car_parts.repositories.UserApiRepository

class TireProductViewModelFactory (private val dataSource: TireProductApiRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TireProductViewModel::class.java)){
            return TireProductViewModel(dataSource) as T
        }
        throw IllegalAccessException("Unknown ViewModel class")
    }
}