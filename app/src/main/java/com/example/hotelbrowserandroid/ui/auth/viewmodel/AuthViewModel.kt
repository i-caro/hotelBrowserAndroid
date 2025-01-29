package com.example.hotelbrowserandroid.ui.auth.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.hotelbrowserandroid.data.local.AppDatabase
import com.example.hotelbrowserandroid.data.local.entity.UserEntity
import com.example.hotelbrowserandroid.data.remote.repositories.UserRepository
import kotlinx.coroutines.flow.first


class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val localDb: AppDatabase = AppDatabase.getDatabase(application)
    private val userDao = localDb.userDao()
    private lateinit var usersRepository: UserRepository

    suspend fun login(email: String, password: String): Boolean {
        return try {
            usersRepository.syncUsers()

            val user = localDb.userDao().getUser(email, password)
            user != null
        } catch (e: Exception) {
            Log.e("AuthViewModel", "Login failed", e)
            false
        }
    }

    suspend fun isEmailRegistered(email: String): Boolean {
        return userDao.isEmailRegistered(email)
    }

    suspend fun getLoggedInUser(email: String): UserEntity? {
        return usersRepository.getUserByEmail(email).first()
    }

    suspend fun register(name: String, email: String, password: String, surname: String, phone: String): Boolean {
        return try {
            val newUser = UserEntity(
                id = 0,
                name = name,
                email = email,
                password = password,
                surname = surname,
                phone = phone,
                imgUrl = ""
            )

            usersRepository.insertUser(newUser)
            true
        } catch (e: Exception) {
            Log.e("AuthViewModel", "Registration failed", e)
            false
        }
    }
}