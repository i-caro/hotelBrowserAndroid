package com.example.hotelbrowserandroid.data.remote.repositories

import android.util.Log
import com.example.hotelbrowserandroid.data.local.dao.UserDao
import com.example.hotelbrowserandroid.data.local.entity.UserEntity
import com.example.hotelbrowserandroid.data.remote.api.RegisterRequest
import com.example.hotelbrowserandroid.data.remote.api.StrapiService
import com.example.hotelbrowserandroid.data.remote.api.UserAttributes
import com.example.hotelbrowserandroid.data.remote.api.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val strapiService: StrapiService,
    private val userDao: UserDao
) {
    fun getUsers(): Flow<List<UserEntity>> {
        return userDao.getUsers()
    }

    suspend fun login(email: String, password: String): UserEntity? {
        return userDao.getUser(email, password)
    }

    fun getUserByEmail(email: String): Flow<UserEntity?> {
        return userDao.getUserByEmail(email)
    }

    suspend fun updateUser(currentEmail: String, name: String, newEmail: String, phone: String) {
        return userDao.updateUserProfile(currentEmail, name, newEmail, phone)
    }

    suspend fun insertUser(user: UserEntity) {
        val registerRequest = RegisterRequest(
            data = UserData(
                name = user.name,
                surname = user.surname,
                email = user.email,
                phone = user.phone,
                imgUrl = user.imgUrl,
                password = user.password
            )
        )

        val response = strapiService.addUser(registerRequest)
        val newUser = user.copy(id = response.id)
        userDao.insertUser(newUser)
        Log.d("UserRepository", "Usuario registrado en Strapi y Room con ID: ${newUser.id}")

    }
    suspend fun syncUsers() {
        try {
            val remoteUsers = strapiService.getUsers().dataUser
            val localUsers = remoteUsers.map { userData ->
                UserEntity(
                    id = 0,
                    name = userData.data.attributes.name,
                    surname = userData.data.attributes.surname,
                    email = userData.data.attributes.email,
                    phone = userData.data.attributes.phone,
                    imgUrl = userData.data.attributes.imgUrl!!,
                    password = userData.data.attributes.password,
                )
            }
            userDao.deleteAllUsers()
            userDao.insertUsers(localUsers)
        } catch (e: Exception) {
            Log.e("UserRepository", "Error syncing users: ${e.message}")
        }
    }
}