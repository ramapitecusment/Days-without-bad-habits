package com.example.dayswithoutbadhabits.main.presentation

import androidx.lifecycle.ViewModel
import com.example.dayswithoutbadhabits.core.Init
import com.example.dayswithoutbadhabits.main.domain.Card
import com.example.dayswithoutbadhabits.main.domain.MainInteractor

class MainViewModel(
    private val communication: MainCommunication.Mutable,
    private val interactor: MainInteractor,
) : ViewModel(), Init, MainViewModelActions {

    override fun init(isFirstRun: Boolean) {
        if (isFirstRun) communication.put(MainUiState.AddAll(interactor.cards()))
    }

    override fun addCard(position: Int) {
        communication.put(MainUiState.Replace(position, Card.Make))
    }

    override fun cancelMakeCard(position: Int) {
        communication.put(MainUiState.Replace(position, Card.Add))
    }

    override fun saveNewCard(text: String, position: Int) {
        val card = interactor.newCard(text)
        val canAddNewCard = interactor.canAddNewCard()
        communication.put(MainUiState.Replace(position, card))
        if (canAddNewCard) communication.put(MainUiState.Add(Card.Add))
    }

    override fun editZeroDaysCard(position: Int, card: Card.ZeroDays) {
        communication.put(MainUiState.Replace(position, card.toEditable()))
    }

    override fun cancelEditZeroDaysCard(position: Int, card: Card.ZeroDaysEdit) {
        communication.put(MainUiState.Replace(position, card.toNonEditable()))
    }

    override fun deleteCard(position: Int, id: Long) {
        val canAddNewCard = interactor.canAddNewCard()
        interactor.deleteCard(id)
        communication.put(MainUiState.Remove(position))
        if (!canAddNewCard) communication.put(MainUiState.Add(Card.Add))
    }

    override fun saveEditedZeroDaysCard(text: String, position: Int, id: Long) {
        interactor.updateCard(id, text)
        communication.put(MainUiState.Replace(position, Card.ZeroDays(text, id)))
    }

    override fun editNonZeroDaysCard(position: Int, card: Card.NonZeroDays) {
        communication.put(MainUiState.Replace(position, card.toEditable()))
    }

    override fun cancelEditNonZeroDaysCard(position: Int, card: Card.NonZeroDaysEdit) {
        communication.put(MainUiState.Replace(position, card.toNonEditable()))
    }

    override fun saveEditedNonZeroDaysCard(days: Int, text: String, position: Int, id: Long) {
        interactor.updateCard(id, text)
        communication.put(MainUiState.Replace(position, Card.NonZeroDays(days, text, id)))
    }

    override fun resetNonZeroDaysCard(position: Int, card: Card.NonZeroDaysEdit) {
        card.reset(interactor)
        communication.put(MainUiState.Replace(position, card.toZeroDays()))
    }

}

interface MainViewModelActions : AddCard, CancelMakeCard, SaveNewCard,
    EditZeroDaysCard, CancelEditZeroDaysCard, DeleteCard,
    SaveEditedZeroDaysCard, EditNonZeroDaysCard, CancelEditNonZeroDaysCard,
    SaveEditedNonZeroDaysCard, ResetNonZeroDaysCard

interface AddCard {
    fun addCard(position: Int)
}

interface CancelMakeCard {
    fun cancelMakeCard(position: Int)
}

interface SaveNewCard {
    fun saveNewCard(text: String, position: Int)
}

interface EditZeroDaysCard {
    fun editZeroDaysCard(position: Int, card: Card.ZeroDays)
}

interface CancelEditZeroDaysCard {
    fun cancelEditZeroDaysCard(position: Int, card: Card.ZeroDaysEdit)
}

interface DeleteCard {
    fun deleteCard(position: Int, id: Long)
}

interface SaveEditedZeroDaysCard {
    fun saveEditedZeroDaysCard(text: String, position: Int, id: Long)
}

interface EditNonZeroDaysCard {
    fun editNonZeroDaysCard(position: Int, card: Card.NonZeroDays)
}

interface CancelEditNonZeroDaysCard {
    fun cancelEditNonZeroDaysCard(position: Int, card: Card.NonZeroDaysEdit)
}

interface SaveEditedNonZeroDaysCard {
    fun saveEditedNonZeroDaysCard(days: Int, text: String, position: Int, id: Long)
}

interface ResetNonZeroDaysCard {
    fun resetNonZeroDaysCard(position: Int, card: Card.NonZeroDaysEdit)
}

//class MainViewModel(
//    private val repository: MainRepository,
//    private val communication: Communication.Post<UiState>,
//) : ViewModel() {
//
//    fun init(isFirstRun: Boolean) {
//        if (!isFirstRun) return
//        val days = repository.days()
//        val state: UiState = if (days == 0) UiState.ZeroDays else UiState.NDays(days)
//        communication.put(state)
//    }
//
//    fun reset() {
//        repository.reset()
//        communication.put(UiState.ZeroDays)
//    }
//
//    fun observe(owner: LifecycleOwner, action: (UiState) -> Unit) = communication.observe(owner, action)
//
//}
