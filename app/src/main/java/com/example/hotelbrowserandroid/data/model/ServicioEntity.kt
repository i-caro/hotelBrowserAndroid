package com.example.hotelbrowserandroid.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ServicioEntity")
data class ServicioEntity(
    @PrimaryKey val id: String,
    val name: String,
    val type: String,
    val description: String,
    val location: String,
    val price: Double,
    val available: Boolean,
    val imageUrl: String
)