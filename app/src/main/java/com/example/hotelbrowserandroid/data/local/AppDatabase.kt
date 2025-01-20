package com.example.hotelbrowserandroid.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hotelbrowserandroid.data.local.dao.ReservationDao
import com.example.hotelbrowserandroid.data.local.dao.ServiceDao
import com.example.hotelbrowserandroid.data.local.dao.UserDao
import com.example.hotelbrowserandroid.data.local.entity.BookingEntity
import com.example.hotelbrowserandroid.data.local.entity.ServiceEntity
import com.example.hotelbrowserandroid.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, ServiceEntity::class, BookingEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun serviceDao(): ServiceDao
    abstract fun reservationDao(): ReservationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}