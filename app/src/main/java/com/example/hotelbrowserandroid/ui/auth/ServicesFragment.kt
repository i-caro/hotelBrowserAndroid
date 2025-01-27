package com.example.hotelbrowserandroid.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelbrowserandroid.data.local.AppDatabase
import com.example.hotelbrowserandroid.databinding.FragmentServiceBinding
import com.example.hotelbrowserandroid.ui.adapters.ServiceAdapter
import kotlinx.coroutines.launch

class ServicesFragment : Fragment() {

    private lateinit var binding: FragmentServiceBinding
    private lateinit var appDatabase: AppDatabase
    private lateinit var serviceAdapter: ServiceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the database
        appDatabase = AppDatabase.getDatabase(requireContext())

        // Initialize the adapter
        serviceAdapter = ServiceAdapter(mutableListOf())

        // Setup RecyclerView
        binding.servicesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = serviceAdapter
        }

        // Load and display the services
        loadServices()
    }

    private fun loadServices() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val services = appDatabase.serviceDao().getAllServices()
                serviceAdapter.submitList(services)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error loading services: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
