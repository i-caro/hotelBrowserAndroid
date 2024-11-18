package com.example.hotelbrowserandroid.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,  // Nombre del usuario
    val surname: String,  // Apellido del usuario
    val email: String,  // Correo electrónico para contacto
    val phone: String?,  // Teléfono del usuario, opcional
    val address: String?,  // Dirección, opcional
    val imgUrl: String?,  // URL de la imagen, opcional
)
