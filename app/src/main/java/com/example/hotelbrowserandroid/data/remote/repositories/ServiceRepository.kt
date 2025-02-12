package com.example.hotelbrowserandroid.data.remote.repositories

import android.util.Log
import com.example.hotelbrowserandroid.data.local.dao.ServiceDao
import com.example.hotelbrowserandroid.data.local.entity.ServiceEntity
import com.example.hotelbrowserandroid.data.remote.api.StrapiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceRepository @Inject constructor(
    private val strapiService: StrapiService
) {
    /** Obtiene todos los servicios desde Strapi */
    fun getServices(): Flow<List<ServiceEntity>> {
        return flow {
            try {
                val response = strapiService.getServices()
                val servicesList = response.data.map { serviceData ->
                    ServiceEntity(
                        id = serviceData.id,
                        name = serviceData.attributes.name,
                        type = serviceData.attributes.type,
                        description = serviceData.attributes.description,
                        location = serviceData.attributes.location,
                        available = serviceData.attributes.available,
                        price = serviceData.attributes.price
                    )
                }
                emit(servicesList)
            } catch (e: Exception) {
                Log.e("ServiceRepository", "Error obteniendo servicios de Strapi", e)
                emit(emptyList()) // Devuelve una lista vacía en caso de error
            }
        }
    }

    /** Obtiene un servicio por nombre desde Strapi */
    suspend fun getServiceByName(name: String): ServiceEntity? {
        return try {
            val response = strapiService.getServices()
            response.data.firstOrNull { it.attributes.name == name }?.let { serviceData ->
                ServiceEntity(
                    id = serviceData.id,
                    name = serviceData.attributes.name,
                    type = serviceData.attributes.type,
                    description = serviceData.attributes.description,
                    location = serviceData.attributes.location,
                    available = serviceData.attributes.available,
                    price = serviceData.attributes.price
                )
            }
        } catch (e: Exception) {
            Log.e("ServiceRepository", "Error obteniendo servicio por nombre en Strapi", e)
            null
        }
    }
}