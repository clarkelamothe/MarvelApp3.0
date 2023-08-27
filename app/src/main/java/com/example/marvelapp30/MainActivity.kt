package com.example.marvelapp30

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.marvelapp30.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        installSplashScreen()
        setContentView(binding.root)

        setTopBar()
        setBottomNav()
    }

    private fun setTopBar() {
        setSupportActionBar(binding.incAppbar.materialToolbar)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.characterFragment, R.id.eventFragment
            )
        )

        setupActionBarWithNavController(navHostFragment.navController, appBarConfiguration)
    }

    private fun setBottomNav() {
        binding.bottomNav.apply {
            itemIconTintList = null
            setupWithNavController(navHostFragment.navController)
        }

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment, R.id.signupFragment -> showBottomNav(false)
                else -> showBottomNav(true)
            }
        }
    }

    private fun showBottomNav(show: Boolean) {
        binding.bottomNav.isVisible = show
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fragment_container).navigateUp() || super.onSupportNavigateUp()
    }
}