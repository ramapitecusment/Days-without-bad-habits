package com.example.dayswithoutbadhabits.main.presentation

import com.example.dayswithoutbadhabits.core.Communication

interface MainCommunication {

    interface Put : Communication.Put<MainUiState>

    interface Observe : Communication.Observe<MainUiState>

    interface Mutable : Put, Observe

    class Base : Communication.Abstract<MainUiState>(UiState.ZeroDays), Mutable

}

