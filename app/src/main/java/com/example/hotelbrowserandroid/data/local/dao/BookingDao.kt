package com.example.hotelbrowserandroid.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hotelbrowserandroid.data.local.entity.BookingEntity

@Dao
interface BookingDao {

    @Insert
    suspend fun insertBooking(booking: BookingEntity)

    @Query("SELECT * FROM bookings")
    suspend fun getAllBookings(): List<BookingEntity>

    @Query("SELECT * FROM bookings WHERE userId = :userId")
    suspend fun getBookingsByUser(userId: Int): List<BookingEntity>

    @Query("SELECT * FROM bookings WHERE id = :reservationId")
    suspend fun getBookingById(reservationId: Int): BookingEntity?
}