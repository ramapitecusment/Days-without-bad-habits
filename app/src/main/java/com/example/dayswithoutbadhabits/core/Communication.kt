package com.example.dayswithoutbadhabits.core

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

interface Communication {

    interface Observe<T> {
        fun observe(owner: LifecycleOwner, action: (T) -> Unit)
    }

    interface Put<T> {
        fun put(value: T)
    }

    interface Mutable<T> : Observe<T>, Put<T>

    abstract class Abstract<T>(
        private val initialValue: T,
        private val state: MutableStateFlow<T> = MutableStateFlow(initialValue)
    ) : Mutable<T> {

        override fun observe(owner: LifecycleOwner, action: (T) -> Unit) {
            owner.lifecycleScope.launch {
                owner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    state.onEach(action::invoke).launchIn(this)
                }
            }
        }

        override fun put(value: T) {
            state.value = value
        }

    }

}