package com.example.hotelbrowserandroid.data.remote.api


data class StrapiResponse(
    val dataUser: List<UserData>,
    val dataBooking: List<BookingData>,
    val dataService: List<ServiceData>
)

data class UserData(
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