package com.example.car_parts.`interface`

//import android.content.Context
import java.util.*

object GenerateId {
    fun autoId(): String {
        val rand = Random()
        val builder = StringBuilder()
        val maxRandom = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".length

        for (i in 0..19) {
            builder.append(
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"[rand.nextInt(
                    maxRandom
                )]
            )
        }
        return builder.toString()
    }
}