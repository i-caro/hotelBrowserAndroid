package com.example.hotelbrowserandroid.data.remote.api

import com.example.hotelbrowserandroid.data.local.entity.UserEntity
import com.google.gson.annotations.SerializedName

// Respuesta general de Strapi
data class StrapiResponse(
    @SerializedName("data") val data: List<UserDataWrapper>
)

// Wrapper para datos del usuario en Strapi
data class UserDataWrapper(
    @SerializedName("id") val id: Int,
    @SerializedName("attributes") val attributes: UserData
)

// Modelo de usuario en Strapi
data class UserData(
    @SerializedName("name") val name: String,
    @SerializedName("surname") val surname: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("imgUrl") val imgUrl: String?, // Permitir valores nulos
    @SerializedName("password") val password: String
) {
    fun toEntity(): UserEntity {
        return UserEntity(
            id = null,
            name = name,
            surname = surname,
            email = email,
            phone = phone,
            imgUrl = imgUrl ?: "", // Manejar imgUrl nulo
            password = password
        )
    }
}

// Petición para registrar usuario
data class RegisterRequest(
    @SerializedName("data") val data: UserData
)

// Petición para actualizar usuario
data class UpdateUserRequest(
    @SerializedName("data") val data: Map<String, String>
)

// Respuesta de usuario local
data class UserDataToLocal(
    @SerializedName("id") val id: Int,
    @SerializedName("attributes") val attributes: UserData
)

// Respuesta de reservas en Strapi
data class BookingResponse(
    @SerializedName("data") val data: List<BookingResponseItem>
)

// Elemento de reserva
data class BookingResponseItem(
    @SerializedName("id") val id: Int,
    @SerializedName("attributes") val attributes: BookingData
)

// Modelo de datos de una reserva
data class BookingData(
    @SerializedName("userId") val userId: Int,
    @SerializedName("serviceId") val serviceId: Int,
    @SerializedName("endDate") val endDate: String,
    @SerializedName("startDate") val startDate: String,
    @SerializedName("peopleAmount") val peopleAmount: Int,
    @SerializedName("preferences") val preferences: String,
    @SerializedName("estado") val status: String,
    @SerializedName("totalPayed") val totalPayed: Double
)

// Petición para crear una reserva
data class BookingRequest(
    @SerializedName("data") val data: BookingData
)

// Respuesta de servicios en Strapi
data class ServiceResponse(
    @SerializedName("data") val data: List<ServiceResponseItem>
)

// Elemento de servicio en Strapi
data class ServiceResponseItem(
    @SerializedName("id") val id: Int,
    @SerializedName("attributes") val attributes: ServiceData
)

// Modelo de servicio en Strapi
data class ServiceData(
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("description") val description: String,
    @SerializedName("location") val location: String,
    @SerializedName("available") val available: String,
    @SerializedName("price") val price: Double
)

