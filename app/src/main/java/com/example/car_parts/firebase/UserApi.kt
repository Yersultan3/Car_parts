package com.example.car_parts.firebase

import com.example.car_parts.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserApi {
    private val firebaseCloudstore by lazy { FirebaseFirestore.getInstance() }
    private val users by lazy { firebaseCloudstore.collection(USERS) }
    private val auth by lazy { FirebaseAuth.getInstance() }

    companion object {
        const val USERS = "users"
    }

        fun addUser(user: User) = users.document(auth.currentUser!!.uid).set(user)
}