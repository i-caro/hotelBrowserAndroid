package com.example.hotelbrowserandroid.data.repository

import com.example.hotelbrowserandroid.data.local.UserDao
import com.example.hotelbrowserandroid.data.model.UserEntity
import javax.inject.Inject

class UsersRepository @Inject constructor(private val userDao: UserDao) {
    suspend fun insertUser(userEntity: UserEntity) = userDao.insert(userEntity)
    suspend fun insertAllUsers(userEntity: List<UserEntity>) = userDao.insertAll(userEntity)
    suspend fun updateUser(userEntity: UserEntity) = userDao.update(userEntity)
    suspend fun deleteUser(userEntity: UserEntity) = userDao.delete(userEntity)
    suspend fun getUserById(id: String) = userDao.getUserById(id)
    suspend fun getAllUsers() = userDao.getAllUsers()
}