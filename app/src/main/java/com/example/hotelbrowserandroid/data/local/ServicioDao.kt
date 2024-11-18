package com.example.hotelbrowserandroid.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.hotelbrowserandroid.data.model.ServicioEntity

@Dao
interface ServicioDao {
    @Query("SELECT * FROM servicio WHERE id = :id")
    suspend fun getServicioById(id: String): ServicioEntity?

    @Query("SELECT * FROM servicio")
    suspend fun getAllServicios(): List<ServicioEntity>
}