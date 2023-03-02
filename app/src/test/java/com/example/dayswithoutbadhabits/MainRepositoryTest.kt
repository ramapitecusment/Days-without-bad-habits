package com.example.dayswithoutbadhabits

import com.example.dayswithoutbadhabits.main.data.CacheDataSource
import com.example.dayswithoutbadhabits.main.data.MainRepository
import com.example.dayswithoutbadhabits.main.data.Now
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class MainRepositoryTest {

    private lateinit var now: FakeNow
    private lateinit var repository: MainRepository
    private lateinit var cacheDataSource: FakeDataSource

    @Before
    fun setup() {
        now = FakeNow.Base()
        cacheDataSource = FakeDataSource()
        repository = MainRepository.Base(now, cacheDataSource)
    }

    @Test
    fun test_no_days() {
        now.setNowTime(1544)
        val actual = repository.days()
        assertEquals(0, actual)
        assertEquals(1544, cacheDataSource.time(-1))
    }

    @Test
    fun test_n_days() {
        cacheDataSource.save(2 * 24 * 3600 * 1000)
        now.setNowTime(9 * 24 * 3600 * 1000)
        val actual = repository.days()
        assertEquals(7, actual)
    }

    @Test
    fun test_reset() {
        now.setNowTime(54321)
        repository.reset()
        assertEquals(54321, cacheDataSource.time(-1))
    }

}

private interface FakeNow : Now {

    fun setNowTime(newTime: Long)

    class Base : FakeNow {

        private var time = 0L

        override fun time(): Long {
            return time
        }

        override fun setNowTime(newTime: Long) {
            time = newTime
        }

    }

}

private class FakeDataSource : CacheDataSource {

    private var time: Long = -1

    override fun save(time: Long) {
        this.time = time
    }

    override fun time(default: Long): Long = if (time == -1L) default else time


}