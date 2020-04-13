package com.example.car_parts.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.car_parts.models.User
import com.example.car_parts.repositories.UserRepositoryInterface
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UserViewModel(private val dataSource: UserRepositoryInterface) : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext:CoroutineContext
        get() = Dispatchers.Main + job

    private val mutableResponse = MutableLiveData<Task<Void>?>()
    val response : LiveData<Task<Void>?>
    get() = mutableResponse

    fun addUser(user: User) {
        launch {
            mutableResponse.postValue(dataSource.addUser(user))
        }
    }



}