package com.eneserdogan.hizligeliyocase.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.eneserdogan.hizligeliyocase.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        initializeBottomNavigation()

    }

    private fun initializeBottomNavigation() {
        val navView:BottomNavigationView=findViewById(R.id.nav_view)

        val navController=findNavController(R.id.navHost_fragment)
        val appBarConfiguration= AppBarConfiguration(setOf(R.id.navigation_home,R.id.navigation_products,R.id.navigation_prices,R.id.navigation_profile))
        setupActionBarWithNavController(navController,appBarConfiguration)
        navView.setupWithNavController(navController)

    }
}