package com.example.dayswithoutbadhabits.main.di

import com.example.dayswithoutbadhabits.main.data.CacheDataSource
import com.example.dayswithoutbadhabits.main.data.MainRepository
import com.example.dayswithoutbadhabits.main.data.Now
import com.example.dayswithoutbadhabits.main.presentation.MainCommunication
import com.example.dayswithoutbadhabits.main.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val mainModule = module {

    viewModel { MainViewModel(get(), get()) }

    singleOf(Now::Base) bind Now::class
    singleOf(MainRepository::Base) bind MainRepository::class
    singleOf(CacheDataSource::Base) bind CacheDataSource::class

    singleOf(::ProvideSharedPref)
    singleOf(ProvideSharedPref::provide)

}