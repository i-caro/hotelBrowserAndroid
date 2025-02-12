package com.example.hotelbrowserandroid.ui.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.hotelbrowserandroid.data.local.entity.UserEntity
import com.example.hotelbrowserandroid.data.remote.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val usersRepository: UserRepository
) : ViewModel() {

    suspend fun login(email: String, password: String): Boolean {
        return try {
            usersRepository.login(email, password) != null
        } catch (e: Exception) {
            Log.e("AuthViewModel", "Login failed", e)
            false
        }
    }

    suspend fun getUserByEmail(email: String): UserEntity? {
        return try {
            usersRepository.getUserByEmail(email)
        } catch (e: Exception) {
            Log.e("AuthViewModel", "Login failed", e)
            return null
        }
    }

    suspend fun isEmailRegistered(email: String): Boolean {
        return try {
                usersRepository.getUserByEmail(email) != null
        } catch (e: Exception) {
            Log.e("AuthViewModel", "Email check failed", e)
            false
        }
    }

    suspend fun register(name: String, email: String, password: String, surname: String, phone: String): Boolean {
        return try {
            val user = UserEntity(
                id = null,
                name = name,
                email = email,
                password = password,
                surname = surname,
                phone = phone,
                imgUrl = "placeholder"
            )

            usersRepository.insertUser(user)
            true
        } catch (e: Exception) {
            Log.e("AuthViewModel", "Registration failed", e)
            false
        }
    }
}
