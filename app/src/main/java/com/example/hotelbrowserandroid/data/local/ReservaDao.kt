package com.example.hotelbrowserandroid.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hotelbrowserandroid.data.model.ReservaEntity

@Dao
interface ReservaDao {
    @Query("SELECT * FROM ReservaEntity WHERE id = :id")
    suspend fun getReservaById(id: String): ReservaEntity?

    @Query("SELECT * FROM ReservaEntity WHERE userId = :userId")
    suspend fun getReservasByUserId(userId: String): List<ReservaEntity>

    @Query("SELECT * FROM ReservaEntity")
    suspend fun getAllReservas(): List<ReservaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReserva(reserva: ReservaEntity)
}