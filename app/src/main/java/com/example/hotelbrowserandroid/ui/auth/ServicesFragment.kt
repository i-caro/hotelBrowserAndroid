package com.example.hotelbrowserandroid.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelbrowserandroid.data.remote.repositories.ServiceRepository
import com.example.hotelbrowserandroid.databinding.FragmentServiceBinding
import com.example.hotelbrowserandroid.ui.adapters.ServiceAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ServicesFragment : Fragment() {

    private lateinit var binding: FragmentServiceBinding
    private lateinit var serviceAdapter: ServiceAdapter

    @Inject
    lateinit var serviceRepository: ServiceRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        serviceAdapter = ServiceAdapter(mutableListOf())

        binding.servicesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = serviceAdapter
        }

        loadServices()
    }

    private fun loadServices() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                serviceRepository.getServices().collect { services ->
                    serviceAdapter.submitList(services)
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error loading services: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

