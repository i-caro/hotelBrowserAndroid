package com.example.hotelbrowserandroid.ui.auth

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelbrowserandroid.R
import com.example.hotelbrowserandroid.data.remote.repositories.BookingRepository
import com.example.hotelbrowserandroid.databinding.FragmentBookingBinding
import com.example.hotelbrowserandroid.ui.adapters.BookingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BookingsFragment : Fragment() {

    private lateinit var binding: FragmentBookingBinding
    private lateinit var bookingAdapter: BookingAdapter

    @Inject
    lateinit var bookingRepository: BookingRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookingAdapter = BookingAdapter(emptyList())

        binding.bookingsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = bookingAdapter
        }

        loadBookingsForUser()

        binding.addBookingFab.setOnClickListener {
            findNavController().navigate(R.id.action_bookingsFragment_to_addBookingFragment)
        }
    }

    private fun loadBookingsForUser() {
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("logged_in_user_id", -1)

        if (userId == -1) {
            Log.e("BookingsFragment", "Error: Usuario no encontrado")
            Toast.makeText(requireContext(), "Error: Usuario no encontrado", Toast.LENGTH_SHORT).show()
            return
        }

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                bookingRepository.getBookingsByUserId(userId).collect { bookings ->
                    bookingAdapter.updateBookings(bookings)
                }
            } catch (e: Exception) {
                Log.e("BookingsFragment", "Error cargando reservas", e)
                Toast.makeText(requireContext(), "Error cargando reservas", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
