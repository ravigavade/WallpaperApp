package com.csaim.wallpaperapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.csaim.wallpaperapp.databinding.ActivityAllWallpaperBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllWallpapersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllWallpaperBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllWallpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val wallpaperManager = WallpaperManager()
        var wallpapers = listOf<WallpaperData>()

        val apiKey = getString(com.csaim.wallpaperapp.R.string.apiKey)

        if (isInternetAvailable()) {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    wallpapers = wallpaperManager.retrieveAllWallpaper(apiKey)
                    Log.d("fucal", "calling")
                }
                binding.recyclerView.layoutManager = GridLayoutManager(this@AllWallpapersActivity, 3)
                binding.recyclerView.adapter =
                    wallpaperAdapter(this, wallpapers) { imageUrl ->
                        setWallpaper(imageUrl)
                    }
            }
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
        }
    }

    fun setWallpaper(imageUrl: String) {
        val target = object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {
                try {
                    val wallpaperManager = android.app.WallpaperManager.getInstance(this@AllWallpapersActivity)
                    wallpaperManager.setBitmap(bitmap)
                    Toast.makeText(this@AllWallpapersActivity, "Wallpaper Set Successfully", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this@AllWallpapersActivity, "Failed to set wallpaper", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                Toast.makeText(this@AllWallpapersActivity, "Failed to load image", Toast.LENGTH_SHORT).show()
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                // Optional: you can display a loading placeholder here if needed
            }
        }
        // Load the image with Picasso and pass the target
        Picasso.get().load(imageUrl).into(target)
    }

    fun isInternetAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
}
