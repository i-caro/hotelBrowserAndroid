package com.example.hotelbrowserandroid.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "services")
data class ServiceEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val type: String,
    val description: String,
    val location: String,
    val available: String,
    val price: Double
)