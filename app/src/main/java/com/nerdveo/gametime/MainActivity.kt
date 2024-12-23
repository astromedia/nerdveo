package com.nerdveo.gametime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nerdveo.gametime.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: AvailabilityAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        
        setupUI()
        setupObservers()
    }
    
    private fun setupUI() {
        adapter = AvailabilityAdapter(emptyList()) { dayIndex, region ->
            viewModel.toggleAvailability(dayIndex, region)
        }
        
        binding.availabilityRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }
    
    private fun setupObservers() {
        viewModel.schedule.observe(this) { schedule ->
            adapter.updateSchedule(schedule)
        }
    }
}
