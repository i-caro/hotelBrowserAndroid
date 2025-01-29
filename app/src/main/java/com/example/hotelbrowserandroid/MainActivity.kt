package com.example.hotelbrowserandroid

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.hotelbrowserandroid.data.remote.repositories.ServiceRepository
import com.example.hotelbrowserandroid.ui.auth.viewmodel.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNav: BottomNavigationView
    private val viewModel: UserViewModel by viewModels()
    private lateinit var serviceRepository: ServiceRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.syncUsers()
        populateServices()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener{ _, destination, _ ->
            when(destination.id){
                R.id.loginFragment, R.id.registerFragment ->{
                        bottomNav.visibility = View.GONE
                    }
                else -> bottomNav.visibility = View.VISIBLE
            }

        }
    }

    private fun populateServices() {
        lifecycleScope.launch {
            val existingServices = serviceRepository.getServices()
            if (existingServices.toList().isEmpty()) {
                serviceRepository.insertAllServices(existingServices)
                Toast.makeText(this@MainActivity, "Services added to the database", Toast.LENGTH_SHORT).show()
            }
        }
    }
}