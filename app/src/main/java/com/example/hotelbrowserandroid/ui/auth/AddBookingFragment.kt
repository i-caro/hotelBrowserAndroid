package com.example.hotelbrowserandroid.ui.auth

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.example.hotelbrowserandroid.data.local.entity.ServiceEntity
import com.example.hotelbrowserandroid.data.local.entity.UserEntity
import com.example.hotelbrowserandroid.data.remote.repositories.BookingRepository
import com.example.hotelbrowserandroid.data.remote.repositories.ServiceRepository
import com.example.hotelbrowserandroid.data.remote.repositories.UserRepository
import com.example.hotelbrowserandroid.databinding.FragmentAddBookingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddBookingFragment : Fragment() {

    private lateinit var binding: FragmentAddBookingBinding

    @Inject
    lateinit var serviceRepository: ServiceRepository

    @Inject
    lateinit var bookingRepository: BookingRepository

    private var serviceList: List<ServiceEntity> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadServices() // ✅ Cargar servicios en el Spinner

        binding.addBookingButton.setOnClickListener {
            addBooking()
        }
    }

    private fun loadServices() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                serviceRepository.getServices().collect { services ->
                    serviceList = services  // ✅ Guardamos la lista completa de servicios

                    val serviceNames = services.map { it.name } // ✅ Extraemos solo los nombres

                    val adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item, // ✅ Usa un layout válido
                        serviceNames
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.serviceSpinner.adapter = adapter
                }
            } catch (e: Exception) {
                Log.e("AddBookingFragment", "Error cargando servicios", e)
                Toast.makeText(requireContext(), "Error cargando servicios", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addBooking() {
        val selectedServiceIndex = binding.serviceSpinner.selectedItemPosition
        if (selectedServiceIndex < 0 || serviceList.isEmpty()) {
            Toast.makeText(requireContext(), "Selecciona un servicio", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedService = serviceList[selectedServiceIndex] // ✅ Obtenemos el objeto `ServiceEntity`

        val startDate = binding.startDateInput.text.toString().trim()
        val endDate = binding.endDateInput.text.toString().trim()
        val peopleAmount = binding.peopleAmountInput.text.toString().toIntOrNull() ?: 0
        val preferences = binding.preferencesInput.text.toString().trim()
        val status = binding.statusSpinner.text.toString().trim()
        val totalPayed = binding.totalPriceInput.text.toString().toDoubleOrNull() ?: 0.0

        if (startDate.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                val userId = sharedPreferences.getInt("logged_in_user_id", -1)

                if (userId == -1) {
                    Toast.makeText(requireContext(), "Error: Usuario no encontrado", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                val booking = BookingEntity(
                    id = 0,  // Strapi genera el ID automáticamente
                    serviceId = selectedService.id,
                    userId = userId,
                    startDate = startDate,
                    endDate = endDate,
                    peopleAmount = peopleAmount,
                    preferences = preferences,
                    status = status,
                    totalPayed = totalPayed
                )

                bookingRepository.addBooking(booking)

                Toast.makeText(requireContext(), "Reserva añadida exitosamente", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            } catch (e: Exception) {
                Log.e("AddBookingFragment", "Error al añadir reserva", e)
                Toast.makeText(requireContext(), "Error al añadir reserva", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

