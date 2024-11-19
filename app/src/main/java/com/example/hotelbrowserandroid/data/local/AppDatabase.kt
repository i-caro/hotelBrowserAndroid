package com.example.hotelbrowserandroid.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hotelbrowserandroid.data.model.ServicioEntity
import com.example.hotelbrowserandroid.data.model.UserEntity
import com.example.hotelbrowserandroid.data.model.ReservaEntity

@Database(
    entities = [ServicioEntity::class, UserEntity::class, ReservaEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun servicioDao(): ServicioDao
    abstract fun userDao(): UserDao
    abstract fun reservaDao(): ReservaDao
}