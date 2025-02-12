package com.example.hotelbrowserandroid.ui.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelbrowserandroid.R
import com.example.hotelbrowserandroid.data.local.entity.BookingEntity

class BookingAdapter(private var bookings: List<BookingEntity>) :
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

    fun updateBookings(newBookings: List<BookingEntity>) {
        bookings = newBookings
        notifyDataSetChanged()
    }

    class BookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val serviceNameTextView: TextView = itemView.findViewById(R.id.serviceNameTextView)
        private val startDateTextView: TextView = itemView.findViewById(R.id.startDateTextView)
        private val endDateTextView: TextView = itemView.findViewById(R.id.endDateTextView)
        private val totalPayedTextView: TextView = itemView.findViewById(R.id.totalPayedTextView)

        fun bind(booking: BookingEntity) {
            serviceNameTextView.text = "Service ID: ${booking.serviceId}"
            startDateTextView.text = "Start: ${booking.startDate}"
            endDateTextView.text = "End: ${booking.endDate}"
            totalPayedTextView.text = "Total: $${booking.totalPayed}"
        }
    }
}

