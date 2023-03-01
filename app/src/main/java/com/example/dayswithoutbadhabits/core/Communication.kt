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

    interface Mutate<T> {

        fun put(value: T)

    }

    interface Mutable<T> : Observe<T>, Mutate<T>

    abstract class Abstract<T>(
        protected val state: MutableStateFlow<T>
    ) : Mutable<T> {

        override fun observe(owner: LifecycleOwner, action: (T) -> Unit) {
            owner.lifecycleScope.launch {
                owner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    state.onEach(action::invoke).launchIn(this)
                }
            }
        }

    }

    abstract class Post<T>(initialValue: T) : Abstract<T>(MutableStateFlow(initialValue)) {

        override fun put(value: T) {
            state.value = value
        }

    }

}