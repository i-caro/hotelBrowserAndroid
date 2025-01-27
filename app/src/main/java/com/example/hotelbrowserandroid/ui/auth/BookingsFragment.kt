package com.example.hotelbrowserandroid.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hotelbrowserandroid.R
import com.example.hotelbrowserandroid.data.local.entity.BookingEntity
import com.example.hotelbrowserandroid.databinding.FragmentBookingBinding
import com.example.hotelbrowserandroid.ui.adapters.BookingAdapter

class BookingsFragment : Fragment() {

    private lateinit var binding: FragmentBookingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookings = listOf(
            BookingEntity(1, 101, 201, "2025-01-15", "2025-01-10", 2, "No smoking room", "Confirmed", 500.0),
            BookingEntity(2, 102, 202, "2025-02-20", "2025-02-15", 1, "Late check-in", "Pending", 300.0)
        )

        val adapter = BookingAdapter(bookings)
        binding.bookingsRecyclerView.adapter = adapter

        binding.addBookingButton.setOnClickListener {
            findNavController().navigate(R.id.action_bookingsFragment_to_addBookingFragment)
        }
    }
}