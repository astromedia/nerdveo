package com.nerdveo.gametime

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nerdveo.gametime.databinding.ItemTimeSlotBinding
import java.time.format.DateTimeFormatter

class AvailabilityAdapter(
    private var timeSlots: List<TimeSlot>
) : RecyclerView.Adapter<AvailabilityAdapter.TimeSlotViewHolder>() {

    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    fun updateTimes(newTimeSlots: List<TimeSlot>) {
        timeSlots = newTimeSlots
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeSlotViewHolder {
        val binding = ItemTimeSlotBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TimeSlotViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimeSlotViewHolder, position: Int) {
        holder.bind(timeSlots[position])
    }

    override fun getItemCount(): Int = timeSlots.size

    inner class TimeSlotViewHolder(
        private val binding: ItemTimeSlotBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(timeSlot: TimeSlot) {
            binding.timeText.text = timeSlot.time.format(timeFormatter)
            binding.availabilityStatus.text = if (timeSlot.isAvailable) "Available" else "Busy"
            binding.availabilityStatus.setTextColor(
                binding.root.context.getColor(
                    if (timeSlot.isAvailable) android.R.color.holo_green_dark
                    else android.R.color.holo_red_dark
                )
            )
        }
    }
}
