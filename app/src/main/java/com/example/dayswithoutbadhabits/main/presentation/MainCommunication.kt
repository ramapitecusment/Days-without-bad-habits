package com.example.dayswithoutbadhabits.main.presentation

import com.example.dayswithoutbadhabits.core.Communication
import com.example.dayswithoutbadhabits.main.presentation.UiState

class MainCommunication : Communication.Post<UiState>(UiState.ZeroDays)

