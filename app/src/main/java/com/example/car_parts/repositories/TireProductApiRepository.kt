package com.example.car_parts.repositories

import com.example.car_parts.firebase.TireProductApi
import com.example.car_parts.firebase.UserApi
import com.example.car_parts.models.TireProduct
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TireProductApiRepository(private val dataSource: TireProductApi) : TireProductRepositoryInterface{
    override suspend fun addTireProduct(tireProduct: TireProduct): Task<Void>? {
        return withContext(Dispatchers.IO){
            dataSource.addTireProduct(tireProduct)
        }
    }

}
