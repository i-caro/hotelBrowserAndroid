package com.example.hotelbrowserandroid.data.remote.repositories

import android.util.Log
import com.example.hotelbrowserandroid.data.local.dao.UserDao
import com.example.hotelbrowserandroid.data.local.entity.UserEntity
import com.example.hotelbrowserandroid.data.remote.api.StrapiService
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

    fun getUserByEmail(email: String): Flow<UserEntity?> {
        return userDao.getUserByEmail(email)
    }

    suspend fun updateUser(currentEmail: String, name: String, newEmail: String, phone: String){
        return userDao.updateUserProfile(currentEmail, name, newEmail, phone)
    }

    suspend fun insertUser(user: UserEntity) {
        try {
            strapiService.addUser(user)
        } catch (e: Exception) {
            Log.e("UserRepository", "Error inserting user in Strapi: ${e.message}")

            userDao.insertUser(user)
        }
    }

    suspend fun syncUsers() {
        try {
            val remoteUsers = strapiService.getUsers().dataUser
            val localUsers = remoteUsers.map { userData ->
                UserEntity(
                    id = userData.id,
                    name = userData.attributes.name,
                    surname = userData.attributes.surname,
                    email = userData.attributes.email,
                    phone = userData.attributes.phone,
                    imgUrl = userData.attributes.imgUrl!!,
                    password = userData.attributes.password,
                )
            }
            userDao.deleteAllUsers()
            userDao.insertUsers(localUsers)
        } catch (e: Exception) {
            Log.e("UserRepository", "Error syncing users: ${e.message}")
        }
    }
}