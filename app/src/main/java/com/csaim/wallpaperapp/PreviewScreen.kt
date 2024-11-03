package com.csaim.wallpaperapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.csaim.wallpaperapp.databinding.ActivityPreviewScreenBinding

class PreviewScreen : AppCompatActivity() {

    private lateinit var binding:ActivityPreviewScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPreviewScreenBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_preview_screen)

    }
}