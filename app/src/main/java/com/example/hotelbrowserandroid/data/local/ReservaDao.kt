package com.example.hotelbrowserandroid.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.hotelbrowserandroid.data.model.ReservaEntity

@Dao
interface ReservaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reservaEntity: ReservaEntity)

    @Update
    suspend fun update(reservaEntity: ReservaEntity)

    @Delete
    suspend fun delete(reservaEntity: ReservaEntity)

    @Query("SELECT * FROM reserva WHERE id = :id")
    suspend fun getReservaById(id: String): ReservaEntity?

    @Query("SELECT * FROM reserva")
    suspend fun getAllReservas(): List<ReservaEntity>
}