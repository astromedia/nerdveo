package com.nerdveo.gametime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalTime

class MainViewModel : ViewModel() {
    private val _availableTimes = MutableLiveData<List<TimeSlot>>()
    val availableTimes: LiveData<List<TimeSlot>> = _availableTimes
    
    private val timesList = mutableListOf<TimeSlot>()
    
    fun addAvailableTime(time: LocalTime) {
        timesList.add(TimeSlot(time))
        timesList.sortBy { it.time }
        _availableTimes.value = timesList.toList()
    }
    
    fun removeTime(timeSlot: TimeSlot) {
        timesList.remove(timeSlot)
        _availableTimes.value = timesList.toList()
    }
}

data class TimeSlot(
    val time: LocalTime,
    val isAvailable: Boolean = true,
    val userId: String = "current_user" // In a real app, this would come from authentication
)
