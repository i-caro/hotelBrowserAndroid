package com.example.hotelbrowserandroid.data.remote.repositories

import android.util.Log
import com.example.hotelbrowserandroid.data.local.entity.BookingEntity
import com.example.hotelbrowserandroid.data.remote.api.BookingData
import com.example.hotelbrowserandroid.data.remote.api.BookingRequest
import com.example.hotelbrowserandroid.data.remote.api.StrapiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookingRepository @Inject constructor(
    private val strapiService: StrapiService
) {
    /** Obtiene todas las reservas desde Strapi */
    fun getBookings(): Flow<List<BookingEntity>> {
        return flow {
            try {
                val response = strapiService.getBookings()
                val bookingsList = response.data.map { bookingData ->
                    BookingEntity(
                        id = bookingData.id,
                        userId = bookingData.attributes.userId,
                        serviceId = bookingData.attributes.serviceId,
                        startDate = bookingData.attributes.startDate,
                        endDate = bookingData.attributes.endDate,
                        peopleAmount = bookingData.attributes.peopleAmount,
                        preferences = bookingData.attributes.preferences,
                        status = bookingData.attributes.status,
                        totalPayed = bookingData.attributes.totalPayed
                    )
                }
                emit(bookingsList)
            } catch (e: Exception) {
                Log.e("BookingRepository", "Error obteniendo reservas desde Strapi", e)
                emit(emptyList()) // ✅ Devuelve una lista vacía en caso de error
            }
        }
    }

    /** Obtiene reservas filtradas por ID de usuario */
    fun getBookingsByUserId(userId: Int): Flow<List<BookingEntity>> {
        return flow {
            try {
                val response = strapiService.getBookings()
                val userBookings = response.data
                    .filter { it.attributes.userId == userId } // ✅ Filtra por usuario
                    .map { bookingData ->
                        BookingEntity(
                            id = bookingData.id,
                            userId = bookingData.attributes.userId,
                            serviceId = bookingData.attributes.serviceId,
                            startDate = bookingData.attributes.startDate,
                            endDate = bookingData.attributes.endDate,
                            peopleAmount = bookingData.attributes.peopleAmount,
                            preferences = bookingData.attributes.preferences,
                            status = bookingData.attributes.status,
                            totalPayed = bookingData.attributes.totalPayed
                        )
                    }
                Log.d("BookingRepository", "Reservas filtradas para userId=$userId: ${userBookings.size}") // ✅ Ver cuántas reservas quedan
                emit(userBookings)
            } catch (e: Exception) {
                Log.e("BookingRepository", "Error obteniendo reservas del usuario en Strapi", e)
                emit(emptyList()) // ✅ Devuelve lista vacía si hay error
            }
        }
    }

    /** Añadir una reserva en Strapi */
    suspend fun addBooking(booking: BookingEntity) {
        try {
            val request = BookingRequest(
                data = BookingData(
                    userId = booking.userId,
                    serviceId = booking.serviceId,
                    startDate = booking.startDate,
                    endDate = booking.endDate,
                    peopleAmount = booking.peopleAmount,
                    preferences = booking.preferences,
                    status = booking.status,
                    totalPayed = booking.totalPayed
                )
            )
            strapiService.addBooking(request)
            Log.d("BookingRepository", "Reserva añadida con éxito en Strapi")
        } catch (e: Exception) {
            Log.e("BookingRepository", "Error al añadir reserva en Strapi", e)
        }
    }
}
