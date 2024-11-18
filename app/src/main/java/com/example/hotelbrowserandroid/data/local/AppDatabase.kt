package com.example.hotelbrowserandroid.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hotelbrowserandroid.data.model.ReservaEntity
import com.example.hotelbrowserandroid.data.model.ServicioEntity
import com.example.hotelbrowserandroid.data.model.UserEntity

@Database(
    entities = [ServicioEntity::class, ReservaEntity::class, UserEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun servicioDao(): ServicioDao
    abstract fun reservaDao(): ReservaDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}