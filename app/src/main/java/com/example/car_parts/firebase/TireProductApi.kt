package com.example.car_parts.firebase

import com.example.car_parts.models.TireProduct
import com.example.car_parts.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TireProductApi {
    private val firebaseCloudstore by lazy { FirebaseFirestore.getInstance() }
    private val tireProducts by lazy { firebaseCloudstore.collection(TIRE_PRODUCTS) }
    private val auth by lazy { FirebaseAuth.getInstance() }


    companion object {
        const val TIRE_PRODUCTS = "tireProducts"
    }

    fun addTireProduct(tireProduct: TireProduct) = tireProducts.document(tireProduct.id).set(tireProduct)
}