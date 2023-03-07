package com.example.dayswithoutbadhabits.base

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.example.dayswithoutbadhabits.core.App

class InstrumentationTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        classLoader: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(classLoader, App::class.java.name, context)
    }
}