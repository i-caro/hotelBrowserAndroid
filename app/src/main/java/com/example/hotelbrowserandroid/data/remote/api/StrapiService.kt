package com.example.hotelbrowserandroid.data.remote.api

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
}