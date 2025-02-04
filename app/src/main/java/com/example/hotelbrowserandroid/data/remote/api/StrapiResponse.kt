package com.example.hotelbrowserandroid.data.remote.api


data class StrapiResponse(
    val dataUser: List<UserDataLocal>,
    val dataUserLocal: UserDataToLocal,
    val dataBooking: List<BookingData>,
    val dataService: List<ServiceData>
)

data class RegisterRequest(
    val data: UserData
)

data class UserDataLocal(
    val data: UserDataToLocal
)

data class UserData(
    val name: String,
    val surname: String,
    val email: String,
    val phone: String,
    val imgUrl: String?,
    val password: String
)

data class UserDataToLocal(
    val id: Int,
    val attributes: UserAttributes
)

data class UserAttributes(
    val name: String,
    val surname: String,
    val email: String,
    val phone: String,
    val imgUrl: String?,
    val password: String
)

data class BookingData(
    val id: Int,
    val attributes: BookingAttributes
)

data class BookingAttributes(
    val userId: Int,
    val serviceId: Int,
    val endDate: String,
    val startDate: String,
    val peopleAmount: Int,
    val preferences: String,
    val status: String,
    val totalPayed: Double
)

data class ServiceData(
    val id: Int,
    val attributes: ServiceAttributes
)

data class ServiceAttributes(
    val name: String,
    val type: String,
    val description: String,
    val location: String,
    val available: String,
    val price: Double
)