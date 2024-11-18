package com.example.hotelbrowserandroid.data.repository

import com.example.hotelbrowserandroid.data.local.ReservaDao
import com.example.hotelbrowserandroid.data.model.ReservaEntity
import javax.inject.Inject

class ReservasRepository @Inject constructor(private val reservaDao: ReservaDao) {
    suspend fun insertReserva(reservaEntity: ReservaEntity) = reservaDao.insert(reservaEntity)
    suspend fun updateReserva(reservaEntity: ReservaEntity) = reservaDao.update(reservaEntity)
    suspend fun deleteReserva(reservaEntity: ReservaEntity) = reservaDao.delete(reservaEntity)
    suspend fun getReservaById(id: String) = reservaDao.getReservaById(id)
    suspend fun getAllReservas() = reservaDao.getAllReservas()
}