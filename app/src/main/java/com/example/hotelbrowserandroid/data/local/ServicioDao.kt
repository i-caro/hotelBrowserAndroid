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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(servicioEntity: ServicioEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(servicioEntities: List<ServicioEntity>)

    @Update
    suspend fun update(servicioEntity: ServicioEntity)

    @Delete
    suspend fun delete(servicioEntity: ServicioEntity)

    @Query("SELECT * FROM servicio WHERE id = :id")
    suspend fun getServicioById(id: String): ServicioEntity?

    @Query("SELECT * FROM servicio")
    suspend fun getAllServicios(): List<ServicioEntity>
}