package com.example.dayswithoutbadhabits.main.data

import android.content.SharedPreferences

interface MainRepository {

    fun days(): Int

    fun reset()

    class Base(
        private val now: Now,
        private val cacheDataSource: CacheDataSource,
    ) : MainRepository {

        private val dayMills = 24 * 3600 * 1000

        override fun days(): Int {
            val saved = cacheDataSource.time(-1)
            return if (saved == -1L) {
                reset()
                0
            } else ((now.time() - saved) / dayMills).toInt()

        }

        override fun reset() {
            cacheDataSource.save(now.time())
        }

    }

}

interface Now {

    fun time(): Long

    class Base : Now {

        override fun time() = System.currentTimeMillis()

    }

}

interface CacheDataSource {

    fun save(time: Long)

    fun time(default: Long): Long

    class Base(private val prefs: SharedPreferences) : CacheDataSource {

        override fun save(time: Long) = prefs.edit().putLong(TIME_KEY, time).apply()

        override fun time(default: Long) = prefs.getLong(TIME_KEY, default)

        companion object {
            private const val TIME_KEY = "TIME_KEY"
        }

    }

}