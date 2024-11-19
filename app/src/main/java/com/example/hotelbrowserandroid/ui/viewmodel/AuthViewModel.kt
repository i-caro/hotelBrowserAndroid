package com.example.hotelbrowserandroid.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelbrowserandroid.data.model.UserEntity
import com.example.hotelbrowserandroid.data.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UsersRepository
) : ViewModel() {

    private val _loggedInUser = MutableStateFlow<UserEntity?>(null)
    val loggedInUser: StateFlow<UserEntity?> get() = _loggedInUser

    fun registerUser(user: UserEntity, onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            val success = userRepository.registerUser(user)
            if (success) {
                onSuccess()
            } else {
                onError()
            }
        }
    }

    fun loginUser(email: String, password: String, onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            val user = userRepository.loginUser(email, password)
            if (user != null) {
                _loggedInUser.value = user
                onSuccess()
            } else {
                onError()
            }
        }
    }

    fun logoutUser() {
        viewModelScope.launch {
            _loggedInUser.value?.let {
                userRepository.logoutUser(it.id)
                _loggedInUser.value = null
            }
        }
    }

    fun loadLoggedUser() {
        viewModelScope.launch {
            _loggedInUser.value = userRepository.getLoggedUser()
        }
    }
}
