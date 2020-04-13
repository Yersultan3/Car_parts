package com.example.car_parts.repositories

import com.example.car_parts.models.TireProduct
import com.example.car_parts.models.User
import com.google.android.gms.tasks.Task

interface TireProductRepositoryInterface {
    suspend fun addTireProduct(tireProduct: TireProduct): Task<Void>?
}