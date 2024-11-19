package com.example.hotelbrowserandroid.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ReservaEntity")
data class ReservaEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val serviceId: String,
    val startDate: String,
    val endDate: String,
    val peopleAmount: Int,
    val preferences: String,
    val totalPayed: Double
)
