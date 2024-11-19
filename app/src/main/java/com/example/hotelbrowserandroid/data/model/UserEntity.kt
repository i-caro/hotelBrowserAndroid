package com.example.hotelbrowserandroid.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserEntity")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val surname: String,
    val email: String,
    val phone: String,
    val password: String,
    val token: String? = null
)
