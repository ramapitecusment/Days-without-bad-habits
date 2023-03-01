package com.example.dayswithoutbadhabits.main.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dayswithoutbadhabits.R
import com.example.dayswithoutbadhabits.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = getSharedPreferences("main", Context.MODE_PRIVATE)
        val time = sharedPref.getLong("time", -1)
        if (time == -1L) {
            sharedPref.edit().putLong("time", System.currentTimeMillis()).apply()
            binding.days.text = "0"
            binding.reset.visibility = View.GONE
        } else {
            val days = (System.currentTimeMillis() - time) / (24 * 60 * 60 * 1000)
            if (days == 0L) binding.reset.visibility = View.GONE
            binding.days.text = days.toString()
        }
        binding.reset.setOnClickListener {
            sharedPref.edit().putLong("time", System.currentTimeMillis()).apply()
            binding.days.text = "0"
            binding.reset.visibility = View.GONE
        }
    }

}