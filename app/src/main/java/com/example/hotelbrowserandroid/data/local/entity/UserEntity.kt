package com.example.hotelbrowserandroid.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val phone: String,
    val imgUrl: String
)