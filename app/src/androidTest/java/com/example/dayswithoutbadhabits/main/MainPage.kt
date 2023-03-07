package com.example.dayswithoutbadhabits.main

import com.example.dayswithoutbadhabits.R
import com.example.dayswithoutbadhabits.base.BasePage
import org.junit.Before

open class MainPage : BasePage() {

    protected val days = R.id.days
    protected val reset = R.id.reset

    @Before
    fun setup() {
        init()
        activityScenarioRule.launch()
    }

    protected open fun init() {}


}