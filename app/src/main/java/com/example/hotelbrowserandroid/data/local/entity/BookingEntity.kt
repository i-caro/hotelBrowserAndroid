package com.example.hotelbrowserandroid.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookings")
data class BookingEntity(
    @PrimaryKey val id: Int,
    val userId: Int,
    val serviceId: Int,
    val endDate: String,
    val startDate: String,
    val peopleAmount: Int,
    val preferences: String,
    val status: String,
    val totalPayed: Double
)