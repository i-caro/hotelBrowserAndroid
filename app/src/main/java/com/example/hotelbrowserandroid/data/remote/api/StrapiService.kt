package com.example.hotelbrowserandroid.data.remote.api

import com.example.hotelbrowserandroid.data.local.entity.UserEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface StrapiService {
    @GET("usuarios")
    suspend fun getUsers(): StrapiResponse

    @GET("bookings")
    suspend fun getBookings(): StrapiResponse

    @GET("services")
    suspend fun getServices(): StrapiResponse

    @POST("bookings")
    suspend fun addBooking(): StrapiResponse

    @POST("usuarios")
    suspend fun addUser(@Body user: UserEntity): StrapiResponse
}