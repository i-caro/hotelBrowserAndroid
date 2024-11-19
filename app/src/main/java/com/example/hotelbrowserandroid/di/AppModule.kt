package com.example.hotelbrowserandroid.di

import android.content.Context
import androidx.room.Room
import com.example.hotelbrowserandroid.data.local.AppDatabase
import com.example.hotelbrowserandroid.data.local.ReservaDao
import com.example.hotelbrowserandroid.data.local.ServicioDao
import com.example.hotelbrowserandroid.data.local.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "hotel_browser_db"
        ).build()
    }

    @Provides
    fun provideServicioDao(database: AppDatabase): ServicioDao {
        return database.servicioDao()
    }

    @Provides
    fun provideReservaDao(database: AppDatabase): ReservaDao {
        return database.reservaDao()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }
}