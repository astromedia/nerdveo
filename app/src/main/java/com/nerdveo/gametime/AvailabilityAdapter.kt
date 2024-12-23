package com.nerdveo.gametime

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.time.DayOfWeek
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.nerdveo.gametime.databinding.ItemTimeSlotBinding
import java.time.format.TextStyle
import java.util.Locale

class AvailabilityAdapter(
    private var schedule: List<DaySchedule>,
    private val onTimeSlotClick: (Int, TimeRegion) -> Unit
) : RecyclerView.Adapter<AvailabilityAdapter.DayViewHolder>() {

    fun updateSchedule(newSchedule: List<DaySchedule>) {
        schedule = newSchedule
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val binding = ItemTimeSlotBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(schedule[position], position)
    }

    override fun getItemCount(): Int = schedule.size

    inner class DayViewHolder(
        private val binding: ItemTimeSlotBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(daySchedule: DaySchedule, position: Int) {
            // Set day and number
            binding.dayText.text = daySchedule.date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
            binding.dayNumber.text = daySchedule.date.dayOfMonth.toString()

            // Show week separator if next day is Monday
            binding.weekSeparator.visibility = if (position < schedule.size - 1 && 
                schedule[position + 1].date.dayOfWeek == DayOfWeek.MONDAY) {
                View.VISIBLE
            } else {
                View.GONE
            }

            // Update morning slot
            binding.morningCard.apply {
                setCardBackgroundColor(if (daySchedule.morning.isAvailable) 
                    android.graphics.Color.parseColor("#E3F2FD") 
                    else android.graphics.Color.WHITE)
                strokeColor = if (daySchedule.morning.isAvailable)
                    android.graphics.Color.parseColor("#2196F3")
                    else android.graphics.Color.LTGRAY
                setOnClickListener { onTimeSlotClick(position, TimeRegion.MORNING) }
            }

            // Update afternoon slot
            binding.afternoonCard.apply {
                setCardBackgroundColor(if (daySchedule.afternoon.isAvailable)
                    android.graphics.Color.parseColor("#E3F2FD")
                    else android.graphics.Color.WHITE)
                strokeColor = if (daySchedule.afternoon.isAvailable)
                    android.graphics.Color.parseColor("#2196F3")
                    else android.graphics.Color.LTGRAY
                setOnClickListener { onTimeSlotClick(position, TimeRegion.AFTERNOON) }
            }

            // Update night slot
            binding.nightCard.apply {
                setCardBackgroundColor(if (daySchedule.night.isAvailable)
                    android.graphics.Color.parseColor("#E3F2FD")
                    else android.graphics.Color.WHITE)
                strokeColor = if (daySchedule.night.isAvailable)
                    android.graphics.Color.parseColor("#2196F3")
                    else android.graphics.Color.LTGRAY
                setOnClickListener { onTimeSlotClick(position, TimeRegion.NIGHT) }
            }
        }
    }
}
