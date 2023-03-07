package com.example.dayswithoutbadhabits.main

import org.junit.Test
import org.koin.core.component.inject
import com.example.dayswithoutbadhabits.main.data.CacheDataSource

class MainActivityTest : MainPage() {

    private val cacheDataSource: CacheDataSource by inject()

    override fun init() {
        cacheDataSource.save(System.currentTimeMillis() - 17 * 24 * 3600 * 1000L)
    }

    @Test
    fun test_zero_days() {
        days.checkText("17")
        reset.click()
        days.checkText("0")
        reset.checkInvisible()
    }

}