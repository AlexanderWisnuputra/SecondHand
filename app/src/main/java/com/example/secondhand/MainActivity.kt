package com.example.secondhand

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.secondhand.R
import com.example.secondhand.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.login -> binding.bottomNavigationView.visibility = View.INVISIBLE
                R.id.register ->binding.bottomNavigationView.visibility = View.INVISIBLE
                else -> binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }
        doubleBackToExit()
    }

    private fun doubleBackToExit() {
        var doubleBackPressed: Long = 0
        val toast = Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT)
        this@MainActivity.onBackPressedDispatcher.addCallback(this) {
            if (doubleBackPressed + 2000 > System.currentTimeMillis()) {
                finishAffinity()
                finish()
                toast.cancel()
            } else {
                toast.show()
            }
            doubleBackPressed = System.currentTimeMillis()
        }
    }
}
