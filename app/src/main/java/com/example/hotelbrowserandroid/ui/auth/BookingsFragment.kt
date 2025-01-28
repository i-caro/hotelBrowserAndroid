package com.example.hotelbrowserandroid.ui.auth

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelbrowserandroid.R
import com.example.hotelbrowserandroid.data.local.AppDatabase
import com.example.hotelbrowserandroid.databinding.FragmentBookingBinding
import com.example.hotelbrowserandroid.ui.adapters.BookingAdapter
import kotlinx.coroutines.launch

class BookingsFragment : Fragment() {

    private lateinit var binding: FragmentBookingBinding
    private lateinit var appDatabase: AppDatabase
    private lateinit var bookingAdapter: BookingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appDatabase = AppDatabase.getDatabase(requireContext())
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

        if (userId != -1) {
            viewLifecycleOwner.lifecycleScope.launch {
                val bookings = appDatabase.bookingDao().getBookingsByUserId(userId)
                bookingAdapter.updateBookings(bookings)
            }
        } else {
            Log.e("Error","No se ha encontrado el ID")
        }
    }
}