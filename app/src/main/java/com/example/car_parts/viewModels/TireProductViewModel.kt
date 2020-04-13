package com.example.car_parts.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.car_parts.models.TireProduct
import com.example.car_parts.models.User
import com.example.car_parts.repositories.TireProductRepositoryInterface
import com.example.car_parts.repositories.UserRepositoryInterface
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TireProductViewModel(private val dataSource: TireProductRepositoryInterface) : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext:CoroutineContext
        get() = Dispatchers.Main + job

    private val mutableResponse = MutableLiveData<Task<Void>?>()
    val response : LiveData<Task<Void>?>
    get() = mutableResponse

    fun addTireProduct(tireProduct: TireProduct) {
        launch {
            mutableResponse.postValue(dataSource.addTireProduct(tireProduct))
        }
    }



}