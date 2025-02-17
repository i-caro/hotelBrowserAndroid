package com.example.hotelbrowserandroid.data.remote.api

import com.example.hotelbrowserandroid.data.local.entity.UserEntity


import com.google.gson.annotations.SerializedName

data class StrapiResponse(
    @SerializedName("data") val data: List<UserDataWrapper>
)

data class UserDataWrapper(
    @SerializedName("id") val id: Int,
    @SerializedName("attributes") val attributes: UserData
)

data class UserData(
    @SerializedName("name") val name: String,
    @SerializedName("surname") val surname: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("password") val password: String,
) {
    fun toEntity(): UserEntity {
        return UserEntity(
            id = null,
            name = name,
            surname = surname,
            email = email,
            phone = phone,
            imgUrl = imgUrl,
            password = password
        )
    }
}

data class RegisterRequest(
    @SerializedName("data") val data: UserData
)

data class UserDataToLocal(
    @SerializedName("id") val id: Int,
    @SerializedName("attributes") val attributes: UserData
)

data class BookingResponse(
    @SerializedName("data") val data: List<BookingResponseItem>
)

data class BookingResponseItem(
    @SerializedName("id") val id: Int,
    @SerializedName("attributes") val attributes: BookingData
)

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

data class BookingRequest(
    @SerializedName("data") val data: BookingData
)

data class ServiceResponse(
    @SerializedName("data") val data: List<ServiceResponseItem>
)

data class ServiceResponseItem(
    @SerializedName("id") val id: Int,
    @SerializedName("attributes") val attributes: ServiceData
)

data class ServiceData(
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("description") val description: String,
    @SerializedName("location") val location: String,
    @SerializedName("available") val available: String,
    @SerializedName("price") val price: Double
)

