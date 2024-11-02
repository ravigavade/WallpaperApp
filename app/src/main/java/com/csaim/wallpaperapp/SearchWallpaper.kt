package com.csaim.wallpaperapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.csaim.wallpaperapp.databinding.ActivitySearchWallpaperBinding

class SearchWallpaper : AppCompatActivity() {
    private lateinit var binding:ActivitySearchWallpaperBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySearchWallpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}