package com.example.dayswithoutbadhabits

import androidx.lifecycle.LifecycleOwner
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private lateinit var interactor: FakeInteractor
    private lateinit var communication: FakeMainCommunication

    @Before
    fun init() {
        viewModel = MainViewModel()
        interactor = FakeInteractor(listOf(Card.Add))
        communication = FakeMainCommunication(interactor, communication)
    }

    @Test
    fun `first start`() {
        viewModel.init(isFirstRun == true)
        assertEquals(UiState.Add(Card.Add), communication.list[0])
        assertEquals(1, communication.uiStateUpdateCount)
    }

    @Test
    fun `add first card`() {
        viewModel.init(isFirstRun == true)
        assertEquals(UiState.Add(Card.Add), communication.list[0])
        assertEquals(1, communication.uiStateUpdateCount)

        viewModel.addCard(position = 0)
        assertEquals(UiState.Replace(position = 0, card = Card.Make), communication.list[0])
        assertEquals(2, communication.uiStateUpdateCount)
    }

    @Test
    fun cancel_make_card() {
        viewModel.init(isFirstRun == true)
        assertEquals(UiState.Add(Card.Add), communication.list[0])
        assertEquals(1, communication.uiStateUpdateCount)

        viewModel.addCard(position = 0)
        assertEquals(UiState.Replace(position = 0, card = Card.Make), communication.list[1])
        assertEquals(2, communication.uiStateUpdateCount)

        viewModel.cancelMakeCard(position = 0)
        assertEquals(UiState.Remove(position = 0), communication.list[2])
        assertEquals(UiState.Add(card = Card.Add), communication.list[3])
        assertEquals(4, communication.uiStateUpdateCount)
    }

    @Test
    fun save_first_card() {
        viewModel.init(isFirstRun == true)
        assertEquals(UiState.Add(Card.Add), communication.list[0])
        assertEquals(1, communication.uiStateUpdateCount)

        viewModel.addCard(position = 0)
        assertEquals(UiState.Replace(position = 0, card = Card.Make), communication.list[1])
        assertEquals(2, communication.uiStateUpdateCount)

        interactor.canAddNewCard = true
        viewModel.saveNewCard(text = "days without smoking", position = 0)
        assertEquals("days without smoking", interactor.saveNewCardList[0])
        assertEquals(1, interactor.saveNewCardList.size)
        assertEquals(
            UiState.Replace(position = 0, card = Card.ZeroDays(text = "days without smoking", id = 4L)),
            communication.list[2]
        )
        assertEquals(UiState.Add(Card.Add), communication.list[3])
        assertEquals(4, communication.uiStateUpdateCount)
        assertEquals(1, interactor.canAddNewCardCalledCount)
        assertEquals(true, interactor.canAddNewCard)
    }

    @Test
    fun save_only_one_card() {
        viewModel.init(isFirstRun == true)
        assertEquals(UiState.Add(Card.Add), communication.list[0])
        assertEquals(1, communication.uiStateUpdateCount)

        viewModel.addCard(position = 0)
        assertEquals(UiState.Replace(position = 0, card = Card.Make), communication.list[1])
        assertEquals(2, communication.uiStateUpdateCount)

        interactor.canAddNewCard = false
        viewModel.saveNewCard(text = "days without smoking", position = 0)
        assertEquals("days without smoking", interactor.saveNewCardList[0])
        assertEquals(1, interactor.saveNewCardList.size)
        assertEquals(UiState.Replace(position = 0, card = Card.ZeroDays(text = "days without smoking", 4L)), communication.list[2])
        assertEquals(3, communication.uiStateUpdateCount)
        assertEquals(1, interactor.canAddNewCardCalledCount)
        assertEquals(false, interactor.canAddNewCard)
    }

    @Test
    fun test_edit_zero_days_card_and_cancel() {
        interactor = FakeInteractor(listOf(Card.ZeroDays(text = "days without smoking", id = 1L), Card.Add))

        viewModel.init(isFirstRun == true)
        assertEquals(UiState.Add(Card.ZeroDays(text = "days without smoking", id = 1L), Card.Add), communication.list[0])
        assertEquals(1, communication.uiStateUpdateCount)

        viewModel.editZeroDaysCard(position = 0)
        assertEquals(
            UiState.Replace(position = 0, card = Card.ZeroDaysEdit(text = "days without smoking", id = 1L)),
            communication.list[1]
        )
        assertEquals(2, communication.uiStateUpdateCount)

        viewModel.cancelEditZeroDaysCard(position = 0)
        assertEquals(
            UiState.Replace(
                position = 0,
                card = Card.ZeroDays(text = "days without smoking", id = 1L),
                Card.Add,
            ), communication.list[2]
        )
        assertEquals(3, communication.uiStateUpdateCount)
    }

    @Test
    fun `test delete zero days card when add card present`() {
        interactor = FakeInteractor(listOf(Card.ZeroDays(text = "days without smoking", id = 1L), Card.Add))

        viewModel.init(isFirstRun == true)
        assertEquals(UiState.Add(Card.ZeroDays(text = "days without smoking", id = 1L), Card.Add), communication.list[0])
        assertEquals(1, communication.uiStateUpdateCount)

        viewModel.editZeroDaysCard(position = 0)
        assertEquals(
            UiState.Replace(position = 0, card = Card.ZeroDaysEdit(text = "days without smoking", id = 1L)),
            communication.list[1]
        )
        assertEquals(2, communication.uiStateUpdateCount)

        viewModel.deleteCard(position = 0)
        assertEquals(UiState.Remove(position = 0), communication.list[2])
        assertEquals(3, communication.uiStateUpdateCount)
    }

    @Test
    fun `test delete zero days card when add card not present`() {
        interactor = FakeInteractor(
            listOf(
                Card.ZeroDays(text = "days without smoking", id = 1L),
                Card.ZeroDays(text = "days without alcohol", id = 2L),
            ),
        )

        viewModel.init(isFirstRun == true)
        assertEquals(
            UiState.Add(
                Card.ZeroDays(text = "days without smoking", id = 1L),
                Card.ZeroDays(text = "days without alcohol", id = 2L),
            ),
            communication.list[0],
        )
        assertEquals(1, communication.uiStateUpdateCount)

        viewModel.editZeroDaysCard(position = 1)
        assertEquals(
            UiState.Replace(position = 1, card = Card.ZeroDaysEdit(text = "days without alcohol", id = 2L)),
            communication.list[1]
        )
        assertEquals(2, communication.uiStateUpdateCount)

        viewModel.deleteCard(position = 1)
        assertEquals(UiState.Remove(position = 1), communication.list[2])
        assertEquals(UiState.Add(Card.Add), communication.list[3])
        assertEquals(4, communication.uiStateUpdateCount)
    }

    @Test
    fun `test edit zero days card and save`() {
        interactor = FakeInteractor(
            listOf(
                Card.ZeroDays(text = "days without smoking", id = 1L),
                Card.ZeroDays(text = "days without alcohol", id = 2L),
            ),
        )

        viewModel.init(isFirstRun == true)
        assertEquals(
            UiState.Add(
                Card.ZeroDays(text = "days without smoking", id = 1L),
                Card.ZeroDays(text = "days without alcohol", id = 2L),
            ),
            communication.list[0],
        )
        assertEquals(1, communication.uiStateUpdateCount)

        viewModel.editZeroDaysCard(position = 1)
        assertEquals(
            UiState.Replace(position = 1, card = Card.ZeroDaysEdit(text = "days without alcohol", id = 2L)),
            communication.list[1]
        )
        assertEquals(2, communication.uiStateUpdateCount)

        interactor.canAddNewCard = true
        viewModel.saveEditedZerDaysCard(text = "days without vodka", position = 1)
        assertEquals("days without vodka", interactor.saveNewCardList[0])
        assertEquals(1, interactor.saveNewCardList.size)
        assertEquals(
            UiState.Replace(
                position = 1,
                card = Card.ZeroDaysEdit(text = "days without vodka", id = 2L)
            ),
            communication.list[2]
        )
        assertEquals(3, communication.uiStateUpdateCount)
        assertEquals(1, interactor.canAddNewCardCalledCount)
        assertEquals(true, interactor.canAddNewCard)
    }

}

private class FakeInteractor(
    private val cards: List<Card>
) : MainInteractor() {

    var canAddNewCard = true
    var canAddNewCardCalledCount = 0
    var saveNewCardList = mutableListOf<String>()

    override fun cards(): List<Card> = cards

    override fun canAddNewCard(): Boolean {
        canAddNewCardCalledCount++
        return canAddNewCard
    }

    override fun newCard(text: String): Card {
        saveNewCardList.add(text)
        return Card.ZeroDays(text = text, id = 4L)
    }

}

private class FakeMainCommunication : MainCommunication {

    val list = mutableListOf<UiState>()
    var uiStateUpdateCount = 0

    override fun put(value: UiState) {
        list.add(value)
        uiStateUpdateCount++
    }

    override fun observe(owner: LifecycleOwner, action: (UiState) -> Unit)

}