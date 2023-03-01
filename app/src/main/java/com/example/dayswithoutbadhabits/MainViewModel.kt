package com.example.dayswithoutbadhabits

class MainViewModel(
    private val repository: MainRepository,
    private val communication: MainCommunication,
) {

    fun init(isFirstRun: Boolean) {
        if (!isFirstRun) return
        val days = repository.days()
        val state: UiState = if (days == 0) UiState.ZeroDays else UiState.NDays(days)
        communication.put(state)
    }

}
