package com.csaim.wallpaperapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.csaim.wallpaperapp.databinding.ActivitySearchWallpaperBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchWallpaper : AppCompatActivity() {
    private lateinit var binding:ActivitySearchWallpaperBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySearchWallpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val wallpaperManager = WallpaperManager()
        var wallpapers = listOf<WallpaperData>()

            val apiKey=getString(com.csaim.wallpaperapp.R.string.apiKey)
            var q="nature"

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                wallpapers = wallpaperManager.retrieveCarsWallpaper(q, apiKey)
                Log.d("fucal", "calling")
            }
            binding.recyclerView.layoutManager = GridLayoutManager(this@SearchWallpaper,2)
            binding.recyclerView.adapter = wallpaperAdapter(this@SearchWallpaper, wallpapers) { imageUrl ->
                setWallpaper(imageUrl)
            }
        }

        binding.findBtn.setOnClickListener{
            val search=binding.keyword.getText().toString().trim()


            q=binding.keyword.getText().toString().trim()

            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    wallpapers = wallpaperManager.retrieveCarsWallpaper(q,apiKey)
                    Log.d("fucal","calling")
                }

                binding.recyclerView.layoutManager = GridLayoutManager(this@SearchWallpaper,3)
                binding.recyclerView.adapter = wallpaperAdapter(this@SearchWallpaper, wallpapers) { imageUrl ->
                    setWallpaper(imageUrl)
                }

            }
        }


    }

    fun setWallpaper(imageUrl: String) {
        val target = object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {
                try {
                    val wallpaperManager = android.app.WallpaperManager.getInstance(this@SearchWallpaper)
                    wallpaperManager.setBitmap(bitmap)
                    Toast.makeText(this@SearchWallpaper, "Wallpaper Set Successfully", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this@SearchWallpaper, "Failed to set wallpaper", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                Toast.makeText(this@SearchWallpaper, "Failed to load image", Toast.LENGTH_SHORT).show()
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                // Optional: you can display a loading placeholder here if needed
            }
        }

        // Load the image with Picasso and pass the target
        Picasso.get().load(imageUrl).into(target)
    }
}