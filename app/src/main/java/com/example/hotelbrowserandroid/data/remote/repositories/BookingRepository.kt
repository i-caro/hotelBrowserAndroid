package com.example.hotelbrowserandroid.data.remote.repositories

import android.util.Log
import com.example.hotelbrowserandroid.data.local.dao.BookingDao
import com.example.hotelbrowserandroid.data.local.entity.BookingEntity
import com.example.hotelbrowserandroid.data.remote.api.StrapiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookingRepository @Inject constructor(
    private val strapiService: StrapiService,
    private val bookingDao: BookingDao
) {
    fun getBookings(): Flow<List<BookingEntity>> {
        return bookingDao.getAllBookings()
    }

    suspend fun addBooking(booking: BookingEntity){
        return bookingDao.insertBooking(booking)
    }

    suspend fun syncBookings() {
        try {
            val remoteBookings = strapiService.getBookings().dataBooking
            val localBookings = remoteBookings.map { bookingData ->
                BookingEntity(
                    id = bookingData.id,
                    userId = bookingData.attributes.userId,
                    serviceId = bookingData.attributes.serviceId,
                    endDate = bookingData.attributes.endDate,
                    startDate = bookingData.attributes.startDate,
                    peopleAmount = bookingData.attributes.peopleAmount,
                    preferences = bookingData.attributes.preferences,
                    status = bookingData.attributes.status,
                    totalPayed = bookingData.attributes.totalPayed
                )
            }
            bookingDao.deleteAllBookings()
            bookingDao.insertAll(localBookings)
        } catch (e: Exception) {
            Log.e("UserRepository", "Error syncing users: ${e.message}")
        }
    }
}