package com.example.hotelbrowserandroid.data.repository

import com.example.hotelbrowserandroid.data.local.UserDao
import com.example.hotelbrowserandroid.data.model.UserEntity
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val userDao: UserDao
) {

    suspend fun getAllUsers(): List<UserEntity> {
        return userDao.getAllUsers()
    }

    suspend fun getUserById(id: String): UserEntity? {
        return userDao.getUserById(id)
    }

    suspend fun insertUser(user: UserEntity) {
        userDao.insertUser(user)
    }
}