package com.example.car_parts.repositories

import com.example.car_parts.models.User
import com.google.android.gms.tasks.Task

interface UserRepositoryInterface {
    suspend fun addUser(user: User): Task<Void>?
}