package com.nerdveo.gametime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.temporal.ChronoUnit

enum class TimeRegion {
    MORNING,
    AFTERNOON,
    NIGHT
}

data class DaySchedule(
    val date: LocalDate,
    val morning: TimeSlot,
    val afternoon: TimeSlot,
    val night: TimeSlot
)

data class TimeSlot(
    val timeRegion: TimeRegion,
    var isAvailable: Boolean = false,
    val userId: String = "current_user" // In a real app, this would come from authentication
)

class MainViewModel : ViewModel() {
    private val _schedule = MutableLiveData<List<DaySchedule>>()
    val schedule: LiveData<List<DaySchedule>> = _schedule
    
    private val scheduleList = mutableListOf<DaySchedule>()
    
    init {
        // Initialize schedule with next 14 days including today
        val today = LocalDate.now()
        for (i in 0..13) {
            val date = today.plus(i.toLong(), ChronoUnit.DAYS)
            scheduleList.add(DaySchedule(
                date = date,
                morning = TimeSlot(TimeRegion.MORNING),
                afternoon = TimeSlot(TimeRegion.AFTERNOON),
                night = TimeSlot(TimeRegion.NIGHT)
            ))
        }
        _schedule.value = scheduleList.toList()
    }
    
    fun toggleAvailability(dayIndex: Int, region: TimeRegion) {
        val daySchedule = scheduleList[dayIndex]
        val slot = when (region) {
            TimeRegion.MORNING -> daySchedule.morning
            TimeRegion.AFTERNOON -> daySchedule.afternoon
            TimeRegion.NIGHT -> daySchedule.night
        }
        slot.isAvailable = !slot.isAvailable
        _schedule.value = scheduleList.toList()
    }
}
