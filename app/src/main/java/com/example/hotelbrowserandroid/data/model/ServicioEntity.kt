package com.example.hotelbrowserandroid.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "servicio")
data class ServicioEntity(
    @PrimaryKey val id: String,
    val name: String,  // Nombre del servicio
    val type: String,  // Tipo de servicio
    val description: String,  // Breve descripción del servicio
    val location: String,  // Dirección o ubicación física
    val price: Double,  // Precio base o promedio para reservas
    val imgUrl: String?,  // URL para la imagen
    val available: Boolean  // Estado de disponibilidad
)
