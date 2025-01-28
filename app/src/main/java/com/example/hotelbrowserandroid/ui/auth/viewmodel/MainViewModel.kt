package com.example.hotelbrowserandroid.ui.auth.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelbrowserandroid.data.local.AppDatabase
import com.example.hotelbrowserandroid.data.remote.api.RetrofitInstance
import com.example.hotelbrowserandroid.data.remote.repositories.AppRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AppRepository
    private val userId: Int

    init {
        val localDb = AppDatabase.getDatabase(application)
        val apiService = RetrofitInstance.api
        repository = AppRepository(localDb, apiService)

        val sharedPreferences =
            application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        userId = sharedPreferences.getInt("logged_in_user_id", -1)
    }

    fun syncData() {
        viewModelScope.launch {
            repository.syncUsers()
            repository.syncServices()

            val sharedPreferences =
                getApplication<Application>().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            val userId = sharedPreferences.getInt("logged_in_user_id", -1)

            if (userId != -1) {
                repository.syncBookings(userId)
            }
        }
    }
}