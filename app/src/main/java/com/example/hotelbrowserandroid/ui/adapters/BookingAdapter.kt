package com.example.hotelbrowserandroid.ui.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelbrowserandroid.R
import com.example.hotelbrowserandroid.data.local.entity.BookingEntity

class BookingAdapter(private val bookings: List<BookingEntity>) :
    RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_booking, parent, false)
        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookings[position]
        holder.bind(booking)
    }

    override fun getItemCount(): Int = bookings.size

    class BookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userIdTextView: TextView = itemView.findViewById(R.id.bookingUserId)
        private val serviceIdTextView: TextView = itemView.findViewById(R.id.bookingServiceId)
        private val datesTextView: TextView = itemView.findViewById(R.id.bookingDates)
        private val statusTextView: TextView = itemView.findViewById(R.id.bookingStatus)
        private val totalPayedTextView: TextView = itemView.findViewById(R.id.bookingTotalPayed)

        fun bind(booking: BookingEntity) {
            userIdTextView.text = "User ID: ${booking.userId}"
            serviceIdTextView.text = "Service ID: ${booking.serviceId}"
            datesTextView.text = "From ${booking.startDate} to ${booking.endDate}"
            statusTextView.text = "Status: ${booking.status}"
            totalPayedTextView.text = "Total Paid: $${booking.totalPayed}"
        }
    }
}