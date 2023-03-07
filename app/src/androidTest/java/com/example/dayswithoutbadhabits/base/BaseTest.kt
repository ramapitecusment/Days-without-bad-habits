package com.example.dayswithoutbadhabits.base

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dayswithoutbadhabits.main.presentation.MainActivity
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.core.component.KoinComponent

@RunWith(AndroidJUnit4::class)
abstract class BaseTest: KoinComponent {

    @get:Rule
    val activityScenarioRule = lazyActivityScenarioRule<MainActivity>(false)

}