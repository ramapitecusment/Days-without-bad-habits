package com.example.dayswithoutbadhabits.main.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.example.dayswithoutbadhabits.core.Communication
import com.example.dayswithoutbadhabits.main.data.MainRepository

class MainViewModel(
    private val repository: MainRepository,
    private val communication: Communication.Post<UiState>,
) : ViewModel() {

    fun init(isFirstRun: Boolean) {
        if (!isFirstRun) return
        val days = repository.days()
        val state: UiState = if (days == 0) UiState.ZeroDays else UiState.NDays(days)
        communication.put(state)
    }

    fun reset() {
        repository.reset()
        communication.put(UiState.ZeroDays)
    }

    fun observe(owner: LifecycleOwner, action: (UiState) -> Unit) = communication.observe(owner, action)

}
