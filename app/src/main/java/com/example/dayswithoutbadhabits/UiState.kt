package com.example.dayswithoutbadhabits

sealed class UiState {

    object ZeroDays : UiState()
    class NDays(private val days: Int) : UiState()

}
