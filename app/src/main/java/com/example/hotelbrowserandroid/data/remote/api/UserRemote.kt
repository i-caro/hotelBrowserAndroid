package com.example.hotelbrowserandroid.data.remote.api

data class UserRemote(
    val id: Int,
    val attributes: UserAttributes
)

data class UserAttributes(
    val name: String,
    val surname: String,
    val email: String,
    val phone: String,
    val imgUrl: String,
    val password: String
)