package com.example.dayswithoutbadhabits.main.di

import android.content.Context
import android.content.SharedPreferences

interface SharedPref {

    fun make(context: Context): SharedPreferences

    abstract class Abstract(private val name: String) : SharedPref {
        override fun make(context: Context): SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    class Base : Abstract("badHabits")
    class Test : Abstract("badHabitsTest")

}