package com.nerdveo.gametime

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.nerdveo.gametime.databinding.ActivityMainBinding
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        
        setupUI()
        setupObservers()
    }
    
    private fun setupUI() {
        binding.addTimeButton.setOnClickListener {
            showTimePicker()
        }
        
        binding.availabilityRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = AvailabilityAdapter(emptyList())
        }
    }
    
    private fun setupObservers() {
        viewModel.availableTimes.observe(this) { times ->
            (binding.availabilityRecyclerView.adapter as? AvailabilityAdapter)?.updateTimes(times)
        }
    }
    
    private fun showTimePicker() {
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Select available time")
            .build()
            
        picker.addOnPositiveButtonClickListener {
            val time = LocalTime.of(picker.hour, picker.minute)
            viewModel.addAvailableTime(time)
            Toast.makeText(this, "Time added: ${time.format(DateTimeFormatter.ofPattern("HH:mm"))}", Toast.LENGTH_SHORT).show()
        }
        
        picker.show(supportFragmentManager, "time_picker")
    }
}
