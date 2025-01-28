package com.example.hotelbrowserandroid.data.remote.api

import com.example.hotelbrowserandroid.data.local.entity.BookingEntity
import com.example.hotelbrowserandroid.data.local.entity.ServiceEntity
import com.example.hotelbrowserandroid.data.local.entity.UserEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("users")
    suspend fun createUser(@Body user: UserAttributes)

    @GET("usuarios")
    suspend fun getUsers(): ResponseWrapper<List<UserRemote>>

    @GET("services")
    suspend fun getServices(): List<ServiceEntity>

    @GET("bookings")
    suspend fun getBookings(): List<BookingEntity>

    @POST("bookings")
    suspend fun createBooking(@Body booking: BookingEntity): BookingEntity
}