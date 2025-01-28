package com.example.hotelbrowserandroid.data.remote.repositories

import android.util.Log
import com.example.hotelbrowserandroid.data.local.AppDatabase
import com.example.hotelbrowserandroid.data.local.entity.BookingEntity
import com.example.hotelbrowserandroid.data.local.entity.UserEntity
import com.example.hotelbrowserandroid.data.remote.api.ApiService
import com.example.hotelbrowserandroid.data.remote.api.UserAttributes

class AppRepository(
    private val localDb: AppDatabase,
    private val apiService: ApiService
) {
    suspend fun syncServices() {
        try {
            val remoteServices = apiService.getServices()
            val localServices = localDb.serviceDao().getAllServices()

            if (remoteServices != localServices) {
                localDb.serviceDao().clearAll()
                localDb.serviceDao().insertAll(remoteServices)
            }
        } catch (e: Exception) {
            Log.e("AppRepository", "Failed to sync services", e)
        }
    }

    suspend fun syncBookings(userId: Int) {
        try {
            val remoteBookings = apiService.getBookings().filter { it.userId == userId }
            val localBookings = localDb.bookingDao().getBookingsByUserId(userId)

            if (remoteBookings != localBookings) {
                localDb.bookingDao().clearUserBookings(userId)
                localDb.bookingDao().insertAll(remoteBookings)
            }
        } catch (e: Exception) {
            Log.e("AppRepository", "Failed to sync bookings", e)
        }
    }

    suspend fun syncUsers() {
        try {
            val response = apiService.getUsers()
            val remoteUsers = response.data.map { userRemote ->
                UserEntity(
                    id = userRemote.id,
                    name = userRemote.attributes.name,
                    email = userRemote.attributes.email,
                    password = "",
                    surname = "",
                    phone = "",
                    imgUrl = ""
                )
            }

            localDb.userDao().clearAll()
            localDb.userDao().insertAll(remoteUsers)
        } catch (e: Exception) {
            Log.e("AppRepository", "Failed to sync users", e)
        }
    }

    suspend fun createUser(user: UserEntity) {
        try {
            val userAttributes = UserAttributes(
                name = user.name,
                email = user.email,
                surname = user.surname,
                password = user.password,
                phone = user.phone,
                imgUrl = user.imgUrl
            )
            apiService.createUser(userAttributes)
        } catch (e: Exception) {
            Log.e("AppRepository", "Failed to create user", e)
        }
    }

    suspend fun createBooking(booking: BookingEntity) {
        try {
            apiService.createBooking(booking)
            localDb.bookingDao().insertBooking(booking)
        } catch (e: Exception) {
            Log.e("AppRepository", "Failed to create booking", e)
        }
    }
}