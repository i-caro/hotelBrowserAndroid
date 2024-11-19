package com.example.hotelbrowserandroid.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelbrowserandroid.databinding.ItemReservaBinding
import com.example.hotelbrowserandroid.data.model.ReservaEntity

class ReservasAdapter : ListAdapter<ReservaEntity, ReservasAdapter.BookingViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val binding = ItemReservaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BookingViewHolder(private val binding: ItemReservaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(booking: ReservaEntity) {
            binding.reserva = booking
            binding.executePendingBindings()
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ReservaEntity>() {
        override fun areItemsTheSame(oldItem: ReservaEntity, newItem: ReservaEntity): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ReservaEntity, newItem: ReservaEntity): Boolean =
            oldItem == newItem
    }
}