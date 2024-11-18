package com.example.hotelbrowserandroid.data.repository

import com.example.hotelbrowserandroid.data.local.ServicioDao
import com.example.hotelbrowserandroid.data.model.ServicioEntity
import javax.inject.Inject

class ServiciosRepository @Inject constructor(private val servicioDao: ServicioDao) {
    suspend fun insertServicio(service: ServicioEntity) = servicioDao.insert(service)
    suspend fun insertAllServicios(servicioEntities: List<ServicioEntity>) = servicioDao.insertAll(servicioEntities)
    suspend fun updateServicio(servicioEntity: ServicioEntity) = servicioDao.update(servicioEntity)
    suspend fun deleteServicio(servicioEntity: ServicioEntity) = servicioDao.delete(servicioEntity)
    suspend fun getServicioById(id: String) = servicioDao.getServicioById(id)
    suspend fun getAllServicios() = servicioDao.getAllServicios()
}