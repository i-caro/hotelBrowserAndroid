package com.example.hotelbrowserandroid.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hotelbrowserandroid.data.local.entity.BookingEntity

@Dao
interface BookingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(bookings: List<BookingEntity>)

    @Query("DELETE FROM bookings WHERE userId = :userId")
    suspend fun clearUserBookings(userId: Int)

    @Insert
    suspend fun insertBooking(booking: BookingEntity)

    @Query("SELECT * FROM bookings")
    suspend fun getAllBookings(): List<BookingEntity>

    @Query("SELECT * FROM bookings WHERE userId = :userId")
    suspend fun getBookingsByUserId(userId: Int): List<BookingEntity>

    @Query("SELECT * FROM bookings WHERE id = :reservationId")
    suspend fun getBookingById(reservationId: Int): BookingEntity?
}