package com.example.hotelbrowserandroid.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "reserva",
    foreignKeys = [
        ForeignKey(
            entity = ServicioEntity::class,
            parentColumns = ["id"],
            childColumns = ["servicioId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["servicioId"]), Index(value = ["userId"])]
)
data class ReservaEntity(
    @PrimaryKey val id: String,
    val servicioId: Int,  // ID del servicio reservado
    val userId: Int,  // ID del usuario que hizo la reserva
    val fechaStart: String,  // Fecha de inicio de la reserva
    val fechaFin: String,  // Fecha de finalización
    val numberPeople: Int,  // Número de personas para la reserva
    val preferences: String?,  // Preferencias del usuario
    val status: String,  // Estado de la reserva
    val totalPayed: Double  // Precio total pagado
)
