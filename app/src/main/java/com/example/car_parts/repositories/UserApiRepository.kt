package com.example.car_parts.repositories

import com.example.car_parts.firebase.UserApi
import com.example.car_parts.models.User
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserApiRepository(private val dataSource: UserApi) : UserRepositoryInterface{
    override suspend fun addUser(user: User): Task<Void>? {
        return withContext(Dispatchers.IO){
            dataSource.addUser(user)
        }
    }

}
