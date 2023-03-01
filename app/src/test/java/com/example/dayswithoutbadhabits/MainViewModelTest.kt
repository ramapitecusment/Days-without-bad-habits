package com.example.dayswithoutbadhabits

import org.junit.Test

class MainViewModelTest {

    @Test
    fun test_zero_days() {
        val repository = FakeRepository(0)
        val communication = FakeMainCommunication.Base()
        val viewModel = MainViewModel(repository, communication)
        viewModel.init(isFirstRun = true)
        assert(communication.checkCalledCount(1))
        assert(communication.isSame(UiState.ZeroDays))
        viewModel.init(isFirstRun = false)
        assert(communication.checkCalledCount(1))
    }

    @Test
    fun test_n_days() {
        val repository = FakeRepository(5)
        val communication = FakeMainCommunication.Base()
        val viewModel = MainViewModel(repository, communication)
        viewModel.init(isFirstRun = true)
        assert(communication.checkCalledCount(5))
        assert(communication.isSame(UiState.NDays(days = 5)))
        viewModel.init(isFirstRun = false)
        assert(communication.checkCalledCount(5))
    }

    fun testDays() {

    }

}

private class FakeRepository(private val days: Int) : MainRepository {

    override fun days() = days

}

interface FakeMainCommunication : MainCommunocation.Put {

    fun checkCalledCount(count: Int): Boolean
    fun isSame(state: UiState): Boolean

    class Base : FakeMainCommunication {

        private lateinit var state: UiState
        private var putCallesCount = 0

        override fun checkCalledCount(count: Int) = putCallesCount == count

        override fun isSame(state: UiState) = this.state.equals(state)

        override fun put(value: UiState) {
            putCallesCount++
            state = value
        }

    }


}