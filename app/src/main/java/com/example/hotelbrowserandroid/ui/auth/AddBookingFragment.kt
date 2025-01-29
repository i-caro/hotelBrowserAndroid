package com.example.hotelbrowserandroid.ui.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hotelbrowserandroid.R
import com.example.hotelbrowserandroid.data.local.AppDatabase
import com.example.hotelbrowserandroid.data.local.entity.BookingEntity
import com.example.hotelbrowserandroid.data.local.entity.UserEntity
import com.example.hotelbrowserandroid.data.remote.repositories.BookingRepository
import com.example.hotelbrowserandroid.data.remote.repositories.ServiceRepository
import com.example.hotelbrowserandroid.data.remote.repositories.UserRepository
import com.example.hotelbrowserandroid.databinding.FragmentAddBookingBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AddBookingFragment : Fragment() {

    private lateinit var binding: FragmentAddBookingBinding
    private lateinit var usersRepository: UserRepository
    private lateinit var bookingRepository: BookingRepository
    private lateinit var serviceRepository: ServiceRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            serviceRepository.getServices().collect { services ->
                val adapter = ArrayAdapter(
                    requireContext(),
                    R.layout.item_service,
                    services.map { it.name }
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.serviceSpinner.adapter = adapter
            }
        }

        binding.addBookingButton.setOnClickListener {
            addBooking()
        }
    }


    private fun addBooking() {
        val selectedServiceName = binding.serviceSpinner.selectedItem
        val startDate = binding.startDateInput.text.toString()
        val endDate = binding.endDateInput.text.toString()
        val peopleAmount = binding.peopleAmountInput.text.toString().toIntOrNull() ?: 0
        val preferences = binding.preferencesInput.text.toString()
        val status = binding.statusSpinner.selectedItem.toString()
        val totalPayed = binding.totalPriceInput.text.toString().toDoubleOrNull() ?: 0.0

        viewLifecycleOwner.lifecycleScope.launch {
            val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            val email = sharedPreferences.getString("logged_in_user_email", null)
            val user: UserEntity? = usersRepository.getUserByEmail(email!!).first()

            val selectedService = serviceRepository.getServicesByName(selectedServiceName.toString())
            if (selectedService != null) {
                val booking = BookingEntity(
                    id = 0,
                    serviceId = selectedService.id,
                    userId = user!!.id,
                    startDate = startDate,
                    endDate = endDate,
                    peopleAmount = peopleAmount,
                    preferences = preferences,
                    status = status,
                    totalPayed = totalPayed
                )
                bookingRepository.addBooking(booking)
                Toast.makeText(requireContext(), "Booking added successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            } else {
                Toast.makeText(requireContext(), "Service not found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}