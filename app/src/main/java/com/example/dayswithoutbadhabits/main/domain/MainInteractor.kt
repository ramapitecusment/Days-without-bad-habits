package com.example.dayswithoutbadhabits.main.domain

interface MainInteractor {

    fun cards(): List<Card>

    fun canAddNewCard(): Boolean

    fun newCard(text: String): Card

    fun deleteCard(id: Long)

    fun updateCard(id: Long, newText: String)

    fun resetCard(id: Long)

    fun moveCardUp(position: Int)

    fun moveCardDown(position: Int)

}