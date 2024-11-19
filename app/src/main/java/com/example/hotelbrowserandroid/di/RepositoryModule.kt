package com.example.hotelbrowserandroid.di

import com.example.hotelbrowserandroid.data.local.ReservaDao
import com.example.hotelbrowserandroid.data.local.ServicioDao
import com.example.hotelbrowserandroid.data.local.UserDao
import com.example.hotelbrowserandroid.data.repository.ReservasRepository
import com.example.hotelbrowserandroid.data.repository.ServiciosRepository
import com.example.hotelbrowserandroid.data.repository.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideServiciosRepository(servicioDao: ServicioDao): ServiciosRepository {
        return ServiciosRepository(servicioDao)
    }

    @Provides
    @Singleton
    fun provideUsersRepository(userDao: UserDao): UsersRepository {
        return UsersRepository(userDao)
    }

    @Provides
    @Singleton
    fun provideReservasRepository(reservaDao: ReservaDao): ReservasRepository {
        return ReservasRepository(reservaDao)
    }
}