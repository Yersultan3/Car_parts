package com.example.car_parts.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.firestore.FirebaseFirestore

class AuthApi {
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val firebaseCloudstore by lazy { FirebaseFirestore.getInstance() }
    private val users by lazy { firebaseCloudstore.collection(USERS) }

    companion object{
     private const val USERS = "users"
    }

        fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) = auth.signInWithCredential(credential)
        fun isUserRegistered(phoneNumber: String) = users.whereEqualTo("phoneNumber", phoneNumber).get()
    }