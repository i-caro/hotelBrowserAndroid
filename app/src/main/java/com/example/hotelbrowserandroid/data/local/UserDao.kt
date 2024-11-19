package com.example.hotelbrowserandroid.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.hotelbrowserandroid.data.model.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity WHERE id = :id")
    suspend fun getUserById(id: String): UserEntity?

    @Query("SELECT * FROM UserEntity")
    suspend fun getAllUsers(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM UserEntity WHERE email = :email AND password = :password LIMIT 1")
    suspend fun loginUser(email: String, password: String): UserEntity?

    @Query("SELECT * FROM UserEntity WHERE token IS NOT NULL LIMIT 1")
    suspend fun getLoggedUser(): UserEntity?

    @Query("UPDATE UserEntity SET token = null WHERE id = :id")
    suspend fun logoutUser(id: String)
}