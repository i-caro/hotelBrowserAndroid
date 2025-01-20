package com.example.hotelbrowserandroid.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hotelbrowserandroid.data.local.entity.BookingEntity

@Dao
interface ReservationDao {

    @Insert
    suspend fun insertReservation(reservation: BookingEntity)

    @Query("SELECT * FROM bookings")
    suspend fun getAllReservations(): List<BookingEntity>

    @Query("SELECT * FROM bookings WHERE userId = :userId")
    suspend fun getReservationsByUser(userId: Int): List<BookingEntity>

    @Query("SELECT * FROM bookings WHERE id = :reservationId")
    suspend fun getReservationById(reservationId: Int): BookingEntity?
}