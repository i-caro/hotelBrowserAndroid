package com.example.hotelbrowserandroid.ui.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.hotelbrowserandroid.data.local.entity.UserEntity
import com.example.hotelbrowserandroid.data.remote.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val usersRepository: UserRepository
) : ViewModel() {

    suspend fun login(email: String, password: String): Boolean {
        return try {
            usersRepository.syncUsers()
            val user = usersRepository.getUserByEmail(email).first()
            user != null
        } catch (e: Exception) {
            Log.e("AuthViewModel", "Login failed", e)
            false
        }
    }

    suspend fun isEmailRegistered(email: String): Boolean {
        return usersRepository.getUserByEmail(email).first() != null
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