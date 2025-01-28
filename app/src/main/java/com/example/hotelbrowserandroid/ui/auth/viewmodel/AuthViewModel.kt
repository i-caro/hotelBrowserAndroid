package com.example.hotelbrowserandroid.ui.auth.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.hotelbrowserandroid.data.local.AppDatabase
import com.example.hotelbrowserandroid.data.local.entity.UserEntity
import com.example.hotelbrowserandroid.data.remote.api.RetrofitInstance
import com.example.hotelbrowserandroid.data.remote.repositories.AppRepository


class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val localDb: AppDatabase = AppDatabase.getDatabase(application)
    private val userDao = localDb.userDao()
    private val apiService = RetrofitInstance.api
    private val repository: AppRepository = AppRepository(localDb,apiService)

    suspend fun login(email: String, password: String): Boolean {
        return try {
            repository.syncUsers()

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
        return userDao.getUserByEmail(email)
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

            repository.createUser(newUser)
            true
        } catch (e: Exception) {
            Log.e("AuthViewModel", "Registration failed", e)
            false
        }
    }
}