package com.example.hotelbrowserandroid.ui.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelbrowserandroid.data.local.entity.UserEntity
import com.example.hotelbrowserandroid.data.remote.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {


    fun getUsers(): Flow<List<UserEntity>>{
        return userRepository.getUsers()
    }

    suspend fun getUserByEmail(email: String): UserEntity? {
        return userRepository.getUserByEmail(email)
    }

    suspend fun updateUser(currentEmail: String, name: String, newEmail: String, phone:String){
        return userRepository.updateUser(currentEmail, name, newEmail, phone)
    }
}