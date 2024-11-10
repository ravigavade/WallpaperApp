package com.csaim.wallpaperapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.csaim.wallpaperapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!NetworkUtils.isInternetAvailable(this)) {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show()
//            finish() // Close the app
        } else {
            // Set the status bar color to black
            window.statusBarColor = ContextCompat.getColor(this, android.R.color.black)

            // Optional: Change the status bar icons to be light (white) for better visibility on dark background
            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false

            val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
            bottomNavigationView.setBackgroundColor(ContextCompat.getColor(this, R.color.bg))

            // Set default fragment
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, MainFragment())
                    .commit()
            }
            bottomNavigationView.setOnNavigationItemSelectedListener { item ->
                val selectedFragment = when (item.itemId) {
                    R.id.nav_main -> MainFragment()
                    R.id.nav_search -> SearchFragment()
                    R.id.nav_account -> AllWallpapersFragment()
                    else -> null
                }
                selectedFragment?.let {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, it)
                        .commit()
                    true
                } ?: false
            }
        }



    }

}