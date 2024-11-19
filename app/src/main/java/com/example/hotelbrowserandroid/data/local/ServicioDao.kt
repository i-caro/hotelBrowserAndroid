package com.example.hotelbrowserandroid.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.hotelbrowserandroid.data.model.ServicioEntity

@Dao
interface ServicioDao {
    @Query("SELECT * FROM ServicioEntity")
    suspend fun getAllServicios(): List<ServicioEntity>

    @Query("SELECT * FROM ServicioEntity WHERE id = :id")
    suspend fun getServicioById(id: String): ServicioEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(servicios: List<ServicioEntity>)
}