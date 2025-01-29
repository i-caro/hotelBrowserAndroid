package com.example.hotelbrowserandroid.data.local

import android.content.Context
import androidx.room.Room
import com.example.hotelbrowserandroid.data.local.dao.UserDao
import com.example.hotelbrowserandroid.data.local.dao.ServiceDao
import com.example.hotelbrowserandroid.data.local.dao.BookingDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    fun provideServiceDao(appDatabase: AppDatabase): ServiceDao {
        return appDatabase.serviceDao()
    }

    @Provides
    fun provideBookingDao(appDatabase: AppDatabase): BookingDao {
        return appDatabase.bookingDao()
    }
}