package com.example.hotelbrowserandroid.data.repository

import com.example.hotelbrowserandroid.data.local.UserDao
import com.example.hotelbrowserandroid.data.model.UserEntity
import com.example.hotelbrowserandroid.ui.adapters.UsuarioAdapter
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

    suspend fun registerUser(user: UserEntity): Boolean {
        val existingUser = userDao.loginUser(user.email, user.password)
        return if (existingUser == null) {
            userDao.insertUser(user)
            true
        } else {
            false
        }
    }

    suspend fun loginUser(email: String, password: String): UserEntity? {
        val user = userDao.loginUser(email, password)
        user?.let {
            val updatedUser = it.copy(token = generateToken())
            userDao.insertUser(updatedUser)
        }
        return user
    }

    suspend fun getLoggedUser(): UserEntity? {
        return userDao.getLoggedUser()
    }

    suspend fun logoutUser(userId: String) {
        userDao.logoutUser(userId)
    }

    private fun generateToken(): String {
        return (1..32).map { ('a'..'z').random() }.joinToString("")
    }
}