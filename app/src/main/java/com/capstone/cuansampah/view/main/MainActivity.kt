package com.capstone.cuansampah.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.cuansampah.R
import com.capstone.cuansampah.databinding.ActivityMainBinding
import com.capstone.cuansampah.view.history.HistoryActivity
import com.capstone.cuansampah.view.market.order.CartActivity
import com.capstone.cuansampah.view.onboarding.OnboardingActivity
import com.capstone.cuansampah.view.viewModel.AuthViewModel
import com.capstone.cuansampah.view.viewModel.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController
    private val viewModel: AuthViewModel by viewModels { ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, OnboardingActivity::class.java))
                finish()
            }
        }
        bottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment)

        bottomNavigationView.setupWithNavController(navController)
        setupView()
    }

    private fun setupView(){
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            R.id.cart_menu -> {
                startActivity(Intent(this, CartActivity::class.java))
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

}
