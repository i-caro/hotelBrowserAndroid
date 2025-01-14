package com.example.hotelbrowserandroid.ui.auth.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.hotelbrowserandroid.data.local.AppDatabase
import com.example.hotelbrowserandroid.data.local.entity.UserEntity

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = AppDatabase.getDatabase(application).userDao()

    suspend fun login(email: String, password: String): Boolean {
        return userDao.getUser(email, password) != null
    }

    suspend fun isEmailRegistered(email: String): Boolean {
        return userDao.isEmailRegistered(email)
    }

    suspend fun register(name: String, email: String, password: String): Boolean {
        return if (userDao.isEmailRegistered(email)) {
            false // Email already registered
        } else {
            userDao.insertUser(UserEntity(name = name, email = email, password = password))
            true
        }
    }
}