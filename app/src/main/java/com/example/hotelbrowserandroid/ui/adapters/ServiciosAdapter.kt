package com.example.hotelbrowserandroid.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelbrowserandroid.databinding.ItemServiceBinding
import com.example.hotelbrowserandroid.data.model.ServicioEntity

class ServiciosAdapter : ListAdapter<ServicioEntity, ServiciosAdapter.ServiceViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val binding = ItemServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ServiceViewHolder(private val binding: ItemServiceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(service: ServicioEntity) {
            binding.service = service
            binding.executePendingBindings()
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ServicioEntity>() {
        override fun areItemsTheSame(oldItem: ServicioEntity, newItem: ServicioEntity): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ServicioEntity, newItem: ServicioEntity): Boolean =
            oldItem == newItem
    }
}