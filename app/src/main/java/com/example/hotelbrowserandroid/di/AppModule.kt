package com.example.hotelbrowserandroid.di

import android.content.Context
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
class AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideServicioDao(database: AppDatabase): ServicioDao {
        return database.servicioDao()
    }

    @Singleton
    @Provides
    fun provideReservaDao(database: AppDatabase): ReservaDao {
        return database.reservaDao()
    }

    @Singleton
    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }
}