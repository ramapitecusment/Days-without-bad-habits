package com.example.dayswithoutbadhabits

import androidx.lifecycle.LifecycleOwner
import org.junit.Before
import org.junit.Test
import kotlin.properties.Delegates

class MainViewModelTest {

    private lateinit var repository: FakeRepository
    private lateinit var communication: FakeMainCommunication
    private lateinit var viewModel: MainViewModel

    @Before
    fun init() {
        repository = FakeRepository()
        communication = FakeMainCommunication.Base()
        viewModel = MainViewModel(repository, communication)
    }

    @Test
    fun test_zero_days() {
        repository.setFakeDays(0)
        viewModel.init(isFirstRun = true)
        assert(communication.checkCalledCount(1))
        assert(communication.isSame(UiState.ZeroDays))
        viewModel.init(isFirstRun = false)
        assert(communication.checkCalledCount(1))
    }

    @Test
    fun test_n_days() {
        repository.setFakeDays(5)
        viewModel.init(isFirstRun = true)
        assert(communication.checkCalledCount(1))
        assert(communication.isSame(UiState.NDays(days = 5)))
        viewModel.init(isFirstRun = false)
        assert(communication.checkCalledCount(1))
    }

    @Test
    fun test_reset() {
        repository.setFakeDays(5)
        viewModel.init(isFirstRun = true)
        assert(communication.checkCalledCount(1))
        assert(communication.isSame(UiState.NDays(days = 5)))
        viewModel.reset()
        assert(communication.checkCalledCount(2))
        assert(communication.isSame(UiState.ZeroDays))
        assert(repository.checkResetCalledCount(1))
    }

}

private class FakeRepository : MainRepository {

    private var days by Delegates.notNull<Int>()
    private var resetCalledCount = 0

    override fun days() = days

    fun setFakeDays(days: Int) {
        this.days = days
    }

    fun checkResetCalledCount(count: Int): Boolean = resetCalledCount == count

    override fun reset() {
        resetCalledCount++
    }

}

private interface FakeMainCommunication : MainCommunication {

    fun checkCalledCount(count: Int): Boolean
    fun isSame(state: UiState): Boolean

    class Base : FakeMainCommunication {

        private lateinit var state: UiState
        private var putCallesCount = 0

        override fun checkCalledCount(count: Int) = putCallesCount == count

        override fun isSame(state: UiState) = this.state == state

        override fun observe(owner: LifecycleOwner, action: (UiState) -> Unit) = Unit

        override fun put(value: UiState) {
            putCallesCount++
            state = value
        }

    }


}