package com.csaim.wallpaperapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.csaim.wallpaperapp.databinding.ActivityWlistBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class wList : AppCompatActivity() {
    private lateinit var binding:ActivityWlistBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityWlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val wallpaperManager = WallpaperManager()
        var wallpapers = listOf<WallpaperData>()

        val q = intent.getStringExtra("Extra").toString()

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                 wallpapers = wallpaperManager.retrieveCarsWallpaper(q)
            }

            val adapter = wallpaperAdapter(wallpapers)
            binding.recyclerView.layoutManager = LinearLayoutManager(this@wList)
            binding.recyclerView.adapter = adapter

        }

    }
}