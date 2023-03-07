package com.example.dayswithoutbadhabits.main.di

import android.content.Context
import android.content.SharedPreferences
import com.example.dayswithoutbadhabits.BuildConfig
import com.example.dayswithoutbadhabits.core.BuildType

class ProvideSharedPref(private val context: Context) {

    fun provide(): SharedPreferences = when (BuildConfig.BUILD_TYPE) {
        BuildType.DEBUG,
        BuildType.RELEASE -> SharedPref.Base().make(context)
        BuildType.TEST -> SharedPref.Test().make(context)
        else -> throw IllegalStateException("No such build type: ${BuildConfig.BUILD_TYPE}")
    }

}