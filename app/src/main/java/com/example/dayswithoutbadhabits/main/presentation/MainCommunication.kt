package com.example.dayswithoutbadhabits.main.presentation

import com.example.dayswithoutbadhabits.core.Communication
import com.example.dayswithoutbadhabits.main.presentation.UiState

interface MainCommunication : Communication.Mutable<UiState> {

    class Base : Communication.Post<UiState>(UiState.ZeroDays)

}