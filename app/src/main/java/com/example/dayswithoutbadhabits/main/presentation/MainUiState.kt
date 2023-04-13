package com.example.dayswithoutbadhabits.main.presentation

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.dayswithoutbadhabits.main.domain.Card

sealed class MainUiState {

    data class Add(private val card: Card) : MainUiState()

    data class AddAll(private val cards: List<Card>) : MainUiState()

    data class Replace(private val position: Int, private val card: Card) : MainUiState()

    data class Remove(private val position: Int) : MainUiState()

//    abstract fun apply(daysTextView: TextView, resetButton: Button)
//
//    object ZeroDays : MainUiState() {
//        override fun apply(daysTextView: TextView, resetButton: Button) {
//            daysTextView.text = "0"
//            resetButton.visibility = View.GONE
//        }
//    }
//
//    data class NDays(private val days: Int) : MainUiState() {
//        override fun apply(daysTextView: TextView, resetButton: Button) {
//            daysTextView.text = days.toString()
//            resetButton.visibility = View.VISIBLE
//        }
//    }

}
