package com.example.hotelbrowserandroid.data.remote.api

import com.example.hotelbrowserandroid.data.remote.api.StrapiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://hotelbrowserstrapi.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideStrapiService(retrofit: Retrofit): StrapiService {
        return retrofit.create(StrapiService::class.java)
    }
}