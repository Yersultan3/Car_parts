package com.example.car_parts.di

import androidx.room.Room
import com.example.car_parts.firebase.AuthApi
import com.example.car_parts.firebase.TireProductApi
import com.example.car_parts.firebase.UserApi
import com.example.car_parts.repositories.AuthApiRepository
import com.example.car_parts.repositories.TireProductApiRepository
import com.example.car_parts.repositories.UserApiRepository
import com.example.car_parts.viewModels.AuthViewModel
import com.example.car_parts.viewModels.TireProductViewModel
import com.example.car_parts.viewModels.UserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

//val dbModule = module {
//    single { Room.databaseBuilder(androidContext(),
//        AppDatabase::class.java, AppDatabase.DB_NAME).build() }
//
//    single { get<AppDatabase>().getUserDao() }
//}

val apiModule = module {
    single { UserApi() }
    single { AuthApi() }
    single { TireProductApi() }
}

val repoModule = module {
//    single { UserDaoRepository(get() as UserDao) }
    single { UserApiRepository(get() as UserApi) }
    single { AuthApiRepository(get() as AuthApi) }
    single { TireProductApiRepository(get() as TireProductApi) }
}

val viewModelModule = module {
//    viewModel(named(name = "userDao")) { UserViewModel(get() as UserDaoRepository) }
    viewModel(named(name = "userApi")) { UserViewModel(get() as UserApiRepository) }
    viewModel(named(name = "authApi")) { AuthViewModel(get() as AuthApiRepository) }
    viewModel(named(name = "tireProductApi")) { TireProductViewModel(get() as TireProductApiRepository) }
}