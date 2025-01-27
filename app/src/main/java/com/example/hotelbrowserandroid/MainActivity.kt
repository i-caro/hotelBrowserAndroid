package com.example.hotelbrowserandroid

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.hotelbrowserandroid.data.local.AppDatabase
import com.example.hotelbrowserandroid.data.local.entity.ServiceEntity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        val services = listOf(
            ServiceEntity(name = "Spa", type = "Relaxation", description = "A relaxing spa service", location = "Hotel Level 1", available = "Yes", price = 50.0),
            ServiceEntity(name = "Gym", type = "Fitness", description = "Access to a fully equipped gym", location = "Hotel Level 2", available = "Yes", price = 30.0),
            ServiceEntity(name = "Pool Access", type = "Recreational", description = "Access to the hotel pool", location = "Hotel Level 1", available = "Yes", price = 20.0)
        )

        val appDatabase = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            val existingServices = appDatabase.serviceDao().getAllServices()
            if (existingServices.isEmpty()) {
                appDatabase.serviceDao().insertServices(services)
                Toast.makeText(this@MainActivity, "Services added to the database", Toast.LENGTH_SHORT).show()
            }
        }
    }
}