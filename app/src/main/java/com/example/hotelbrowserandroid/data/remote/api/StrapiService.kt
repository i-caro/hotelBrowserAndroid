package com.example.hotelbrowserandroid.data.remote.api

import retrofit2.http.*

interface StrapiService {

    /** Obtener lista de usuarios */
    @GET("usuarios")
    suspend fun getUsers(): StrapiResponse

    /** Obtener lista de reservas */
    @GET("bookings")
    suspend fun getBookings(): BookingResponse

    /** Obtener lista de servicios */
    @GET("services")
    suspend fun getServices(): ServiceResponse

    /** Crear una reserva */
    @POST("bookings")
    suspend fun addBooking(@Body bookingRequest: BookingRequest): BookingResponseItem

    /** Crear un usuario */
    @POST("usuarios")
    suspend fun addUser(@Body registerRequest: RegisterRequest): UserDataToLocal

    /** Actualizar un usuario por ID */
    @PUT("usuarios/{id}")
    suspend fun updateUser(@Path("id") userId: Int, @Body updateRequest: Map<String, String>): UserDataToLocal

    /** Eliminar un usuario por ID */
    @DELETE("usuarios/{id}")
    suspend fun deleteUser(@Path("id") userId: Int)

    /** Obtener un usuario específico por ID */
    @GET("usuarios/{id}")
    suspend fun getUserById(@Path("id") userId: Int): UserDataToLocal
}