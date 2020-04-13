package com.example.car_parts.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(listOf(
//                dbModule,
                apiModule,
                repoModule,
                viewModelModule
            ))
        }
    }
}