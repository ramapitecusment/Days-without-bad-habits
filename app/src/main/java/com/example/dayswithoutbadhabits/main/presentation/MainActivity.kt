package com.example.dayswithoutbadhabits.main.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dayswithoutbadhabits.R
import com.example.dayswithoutbadhabits.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding(ActivityMainBinding::bind)
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.observe(this) { it.apply(binding.days, binding.reset) }
        binding.reset.setOnClickListener { viewModel.reset() }
        viewModel.init(savedInstanceState == null)
    }

}