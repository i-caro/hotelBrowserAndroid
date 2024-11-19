package com.example.hotelbrowserandroid.data.repository

import com.example.hotelbrowserandroid.data.local.ServicioDao
import com.example.hotelbrowserandroid.data.model.ServicioEntity
import javax.inject.Inject

class ServiciosRepository @Inject constructor(
    private val servicioDao: ServicioDao
) {
    suspend fun getAllServicios(): List<ServicioEntity> {
        return servicioDao.getAllServicios()
    }

    suspend fun getServicioById(id: String): ServicioEntity? {
        return servicioDao.getServicioById(id)
    }

    suspend fun insertAllServicios(servicios: List<ServicioEntity>) {
        servicioDao.insertAll(servicios)
    }
}