package com.example.hotelbrowserandroid.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hotelbrowserandroid.data.local.entity.ServiceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(services: List<ServiceEntity>)

    @Query("DELETE FROM services")
    suspend fun clearAll()

    @Insert
    suspend fun insertService(service: ServiceEntity)

    @Query("SELECT * FROM services")
    fun getAllServices(): Flow<List<ServiceEntity>>

    @Query("SELECT * FROM services WHERE id = :serviceId")
    suspend fun getServiceById(serviceId: Int): ServiceEntity?

    @Query("SELECT * FROM services WHERE name = :name")
    suspend fun getServiceByName(name: String): ServiceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertServices(services: List<ServiceEntity>)
}