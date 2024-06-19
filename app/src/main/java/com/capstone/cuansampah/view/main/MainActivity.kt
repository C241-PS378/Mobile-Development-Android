package com.capstone.cuansampah.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.cuansampah.R
import com.capstone.cuansampah.databinding.ActivityMainBinding
import com.capstone.cuansampah.view.history.HistoryActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment)

        bottomNavigationView.setupWithNavController(navController)
        setupView()
    }

    private fun setupView(){
        supportActionBar?.apply {
            setBackgroundDrawable(null)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if(item.itemId == R.id.history){
//            val intent = Intent(this, HistoryActivity::class.java)
//            startActivity(intent)
//        }
//
//        return super.onOptionsItemSelected(item)
//    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.history -> {
                navController.navigate(R.id.navigation_history)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return when (navController.currentDestination?.id) {
            R.id.navigation_market -> {
                invalidateOptionsMenu()
                navController.navigateUp()
            }
            else -> {
                navController.navigateUp()
            }
        }
    }
//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp() || super.onSupportNavigateUp()
//    }
}
