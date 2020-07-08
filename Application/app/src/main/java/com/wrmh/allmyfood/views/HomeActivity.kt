package com.wrmh.allmyfood.views

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.wrmh.allmyfood.R
import com.wrmh.allmyfood.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =
            DataBindingUtil.setContentView<ActivityHomeBinding>(
                this,
                R.layout.activity_home
            )

        drawerLayout = binding.drawerLayout

        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupWithNavController(binding.navView, navController)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
    }

    override fun onBackPressed() {
        if (
            supportFragmentManager.findFragmentById(R.id.myNavHostFragment)
                ?.childFragmentManager?.fragments?.first()
                ?.javaClass === WelcomeFragment::class.java
        ) {
            Toast.makeText(applicationContext, "¡Vuelve pronto!", Toast.LENGTH_LONG).show()
            finishAffinity()
        }
        else{
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}