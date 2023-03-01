package com.example.dayswithoutbadhabits

import com.example.dayswithoutbadhabits.core.Communication

interface MainCommunication : Communication.Mutable<UiState> {

    class Base : Communication.Post<UiState>(UiState.ZeroDays)

}