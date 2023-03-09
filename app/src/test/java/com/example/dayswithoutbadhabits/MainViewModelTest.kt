package com.example.dayswithoutbadhabits

import androidx.lifecycle.LifecycleOwner
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class MainViewModelTest {

    private lateinit var viewModel: NewMainViewModel
    private lateinit var interactor: FakeInteractor
    private lateinit var communication: FakeMainCommunication

    @Before
    fun init() {
        viewModel = NewMainViewModel()
        interactor = FakeInteractor(listOf(Card.Add))
        communication = FakeMainCommunication(interactor, communication)
    }

    @Test
    fun start() {
        viewModel.init(isFirstRun == true)
        assertEquals(NewUiState.Add(Card.Add), communication.list[0])
        assertEquals(1, communication.list.size)
    }

    @Test
    fun add_first_card() {
        viewModel.init(isFirstRun == true)
        assertEquals(NewUiState.Add(Card.Add), communication.list[0])
        assertEquals(1, communication.list.size)

        viewModel.addCard(position = 0)
        assertEquals(NewUiState.Replace(position = 0, Card.Make), communication.list[0])
        assertEquals(2, communication.list.size)
    }

    @Test
    fun cancel_make_card() {
        viewModel.init(isFirstRun == true)
        assertEquals(NewUiState.Add(Card.Add), communication.list[0])
        assertEquals(1, communication.list.size)

        viewModel.addCard(position = 0)
        assertEquals(NewUiState.Replace(position = 0, Card.Make), communication.list[1])
        assertEquals(2, communication.list.size)

        viewModel.cancelMakeCard(position = 0)
        assertEquals(NewUiState.Replace(position = 0, Card.Add), communication.list[2])
        assertEquals(3, communication.list.size)
    }

    @Test
    fun save_first_card() {
        viewModel.init(isFirstRun == true)
        assertEquals(NewUiState.Add(Card.Add), communication.list[0])
        assertEquals(1, communication.list.size)

        viewModel.addCard(position = 0)
        assertEquals(NewUiState.Replace(position = 0, Card.Make), communication.list[1])
        assertEquals(2, communication.list.size)

        interactor.canAddNewCard = true
        viewModel.saveNewCard(text = "days without smoking", position = 0)
        assertEquals(NewUiState.Replace(position = 0, Card.ZeroDays(text = "days without smoking")), communication.list[2])
        assertEquals(NewUiState.Add(Card.Add), communication.list[3])
        assertEquals(4, communication.list.size)
        assertEquals(1, interactor.canAddNewCardCalledCount)
        assertEquals(true, interactor.canAddNewCard)
    }

    @Test
    fun save_only_one_card() {
        viewModel.init(isFirstRun == true)
        assertEquals(NewUiState.Add(Card.Add), communication.list[0])
        assertEquals(1, communication.list.size)

        viewModel.addCard(position = 0)
        assertEquals(NewUiState.Replace(position = 0, Card.Make), communication.list[1])
        assertEquals(2, communication.list.size)

        interactor.canAddNewCard = false
        viewModel.saveNewCard(text = "days without smoking", position = 0)
        assertEquals(NewUiState.Replace(position = 0, Card.ZeroDays(text = "days without smoking")), communication.list[2])
        assertEquals(3, communication.list.size)
        assertEquals(1, interactor.canAddNewCardCalledCount)
        assertEquals(false, interactor.canAddNewCard)
    }


}

private class FakeInteractor(
    private val cards: List<Card>
) : NewMainInteractor() {

    var canAddNewCard = true
    var canAddNewCardCalledCount = 0

    override fun cards(): List<Card> = cards

    override fun canAddNewCard(): Boolean {
        canAddNewCardCalledCount++
        return canAddNewCard
    }

}

private class FakeMainCommunication : NewMainCommunication {

    val list = mutableListOf<NewUiState>()

    override fun put(value: NewUiState) {
        list.add(value)
    }

    override fun observe(owner: LifecycleOwner, , action: (NewUiState) -> Unit)

}