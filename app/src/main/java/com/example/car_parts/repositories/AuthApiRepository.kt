package com.example.car_parts.repositories

import android.util.Log
import com.example.car_parts.firebase.AuthApi
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthApiRepository(private val authorizationApi: AuthApi) {
    suspend fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) : Task<AuthResult> {
        Log.d("yera repo credential", credential.toString())
        return withContext(Dispatchers.IO) {
            authorizationApi.signInWithPhoneAuthCredential(credential)
        }
    }

    suspend fun isUserRegistered(phoneNumber: String) : Task<QuerySnapshot> {
        return withContext(Dispatchers.IO) {
            authorizationApi.isUserRegistered(phoneNumber)
        }
    }
}