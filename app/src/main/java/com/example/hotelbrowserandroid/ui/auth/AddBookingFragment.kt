package com.example.hotelbrowserandroid.ui.auth

import android.R
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
import com.example.hotelbrowserandroid.data.local.AppDatabase
import com.example.hotelbrowserandroid.data.local.entity.BookingEntity
import com.example.hotelbrowserandroid.data.local.entity.UserEntity
import com.example.hotelbrowserandroid.databinding.FragmentAddBookingBinding
import kotlinx.coroutines.launch

class AddBookingFragment : Fragment() {

    private lateinit var binding: FragmentAddBookingBinding
    private lateinit var appDatabase: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appDatabase = AppDatabase.getDatabase(requireContext())

        // Cargar servicios en el Spinner
        viewLifecycleOwner.lifecycleScope.launch {
            val services = appDatabase.serviceDao().getAllServices()
            val adapter = ArrayAdapter(
                requireContext(),
                R.layout.simple_spinner_item,
                services.map { it.name }
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.serviceSpinner.adapter = adapter
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
            lateinit var user: UserEntity
            if(email!=null){
                user = appDatabase.userDao().getUserByEmail(email)!!
            }
            val selectedService = appDatabase.serviceDao().getServiceByName(selectedServiceName.toString())
            if (selectedService != null) {
                val booking = BookingEntity(
                    serviceId = selectedService.id,
                    userId = user.id,
                    startDate = startDate,
                    endDate = endDate,
                    peopleAmount = peopleAmount,
                    preferences = preferences,
                    status = status,
                    totalPayed = totalPayed
                )
                appDatabase.bookingDao().insertBooking(booking)
                Toast.makeText(requireContext(), "Booking added successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp() // Return to the previous fragment
            } else {
                Toast.makeText(requireContext(), "Service not found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}