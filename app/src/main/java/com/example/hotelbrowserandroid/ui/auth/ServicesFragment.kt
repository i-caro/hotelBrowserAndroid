package com.example.hotelbrowserandroid.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelbrowserandroid.R
import com.example.hotelbrowserandroid.data.local.entity.ServiceEntity
import com.example.hotelbrowserandroid.databinding.FragmentServiceBinding
import com.example.hotelbrowserandroid.ui.adapters.ServiceAdapter

class ServicesFragment : Fragment() {

    private lateinit var binding: FragmentServiceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val services = listOf(
            ServiceEntity(1, "Room Service", "Hotel", "Service in your room", "Hotel XYZ", "Available", 100.0),
            ServiceEntity(2, "Spa Conchi", "Spa", "Relaxing spa package", "Hotel XYZ", "Available", 200.0)
        )

        val adapter = ServiceAdapter(services)
        binding.servicesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.servicesRecyclerView.adapter = adapter
    }
}