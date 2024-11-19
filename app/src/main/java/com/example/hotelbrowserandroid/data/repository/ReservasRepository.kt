package com.example.hotelbrowserandroid.data.repository

import com.example.hotelbrowserandroid.data.local.ReservaDao
import com.example.hotelbrowserandroid.data.model.ReservaEntity
import javax.inject.Inject

class ReservasRepository @Inject constructor(
    private val reservaDao: ReservaDao
) {
    suspend fun getAllReservas(): List<ReservaEntity> {
        return reservaDao.getAllReservas()
    }
    suspend fun getReservaById(id: String): ReservaEntity? {
        return reservaDao.getReservaById(id)
    }

    suspend fun getReservasByUserId(userId: String): List<ReservaEntity> {
        return reservaDao.getReservasByUserId(userId)
    }
    suspend fun insertReserva(reserva: ReservaEntity) {
        reservaDao.insertReserva(reserva)
    }
}