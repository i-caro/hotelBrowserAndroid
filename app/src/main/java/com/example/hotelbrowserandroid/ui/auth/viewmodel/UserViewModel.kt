package com.example.hotelbrowserandroid.ui.auth.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    fun uploadProfileImage(imageUri: Uri, userId: Int): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        viewModelScope.launch {
            try {
                val success = userRepository.uploadImageAndUpdateUser(imageUri, userId)
                result.postValue(success)
                if (success) {
                    Log.d("UserViewModel", "Imagen subida correctamente a Strapi")
                } else {
                    Log.e("UserViewModel", "Error al subir la imagen a Strapi")
                }
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error al actualizar usuario con imagen", e)
                result.postValue(false)
            }
        }

        return result
    }

}