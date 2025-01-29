package com.example.hotelbrowserandroid.data.remote.repositories

import android.util.Log
import com.example.hotelbrowserandroid.data.local.dao.ServiceDao
import com.example.hotelbrowserandroid.data.local.entity.ServiceEntity
import com.example.hotelbrowserandroid.data.remote.api.StrapiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ServiceRepository @Inject constructor(
    private val strapiService: StrapiService,
    private val serviceDao: ServiceDao
) {
    fun getServices(): Flow<List<ServiceEntity>> {
        return serviceDao.getAllServices()
    }

    suspend fun getServicesByName(name: String): ServiceEntity?{
        return serviceDao.getServiceByName(name)
    }

    suspend fun insertAllServices(services: List<ServiceEntity>){
        return serviceDao.insertServices(services)
    }

    suspend fun syncBookings() {
        try {
            val remoteServices = strapiService.getServices().dataService
            val localServices = remoteServices.map { serviceData ->
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
            serviceDao.clearAll()
            serviceDao.insertAll(localServices)
        } catch (e: Exception) {
            Log.e("UserRepository", "Error syncing users: ${e.message}")
        }
    }
}