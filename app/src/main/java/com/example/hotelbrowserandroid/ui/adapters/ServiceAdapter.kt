package com.example.hotelbrowserandroid.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelbrowserandroid.R
import com.example.hotelbrowserandroid.data.local.entity.ServiceEntity
import com.example.hotelbrowserandroid.ui.auth.MapFragment

class ServiceAdapter(
    private var services: MutableList<ServiceEntity>
) : RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_service, parent, false)
        return ServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = services[position]
        holder.bind(service)
    }

    override fun getItemCount(): Int = services.size

    fun submitList(newServices: List<ServiceEntity>) {
        services.clear()
        services.addAll(newServices)
        notifyDataSetChanged()
    }

    class ServiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.serviceName)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.serviceDescription)
        private val priceTextView: TextView = itemView.findViewById(R.id.servicePrice)
        private val btnViewOnMap: Button = itemView.findViewById(R.id.btnViewOnMap)

        fun bind(service: ServiceEntity) {
            nameTextView.text = service.name
            descriptionTextView.text = service.description
            priceTextView.text = "$${service.price}"

            btnViewOnMap.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("service_location", service.location)

                itemView.findNavController().navigate(R.id.action_servicesFragment_to_mapFragment, bundle)
            }
        }
    }
}
