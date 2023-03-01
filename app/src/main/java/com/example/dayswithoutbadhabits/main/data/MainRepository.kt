package com.example.dayswithoutbadhabits.main.data

interface MainRepository {

    fun days(): Int

    fun reset()

}