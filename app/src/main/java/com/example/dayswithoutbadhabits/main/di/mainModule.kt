package com.example.dayswithoutbadhabits.main.di

import android.content.Context
import com.example.dayswithoutbadhabits.main.data.CacheDataSource
import com.example.dayswithoutbadhabits.main.data.MainRepository
import com.example.dayswithoutbadhabits.main.data.Now
import com.example.dayswithoutbadhabits.main.presentation.MainCommunication
import com.example.dayswithoutbadhabits.main.presentation.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    viewModel { MainViewModel(get(), MainCommunication()) }

    single<Now> { Now.Base() }
    single<CacheDataSource> { CacheDataSource.Base(get()) }
    single<MainRepository> { MainRepository.Base(get(), get()) }

    single { androidContext().getSharedPreferences("badHabits", Context.MODE_PRIVATE) }

}