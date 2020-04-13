package com.example.car_parts.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.car_parts.repositories.AuthApiRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AuthViewModel(private val repository: AuthApiRepository): ViewModel(), CoroutineScope {


    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val mutableTask = MutableLiveData<Task<AuthResult>>()
    val task: LiveData<Task<AuthResult>>
        get() = mutableTask

    private val mutableSnapshot = MutableLiveData<Task<QuerySnapshot>>()
    val snapshot: LiveData<Task<QuerySnapshot>>
        get() = mutableSnapshot



    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential){
        Log.d("yera vm credential", credential.toString())
        launch {
            mutableTask.postValue(repository.signInWithPhoneAuthCredential(credential))
            Log.d ("yera vm task ", mutableTask.value.toString())
        }
    }

    fun isUserRegistered (phoneNumber : String){
        launch {
            mutableSnapshot.postValue(repository.isUserRegistered(phoneNumber))
        }
    }

}