package com.csaim.wallpaperapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.csaim.wallpaperapp.databinding.ActivityUnsplashBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Unsplash : AppCompatActivity() {
    private lateinit var binding: ActivityUnsplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUnsplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val wallpaperManager = WallpaperManager()  // Assuming WallpaperManager is your data manager class
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        if (isInternetAvailable()) {
            lifecycleScope.launch {
                try {
                    val wallpapers = withContext(Dispatchers.IO) {
                        wallpaperManager.Unsplash()// Ensure this method exists and returns a list of wallpapers
                    }

                    Log.d("Unsplash", "Fetched ${wallpapers.size} wallpapers")

                    if (wallpapers.isNotEmpty()) {
                        binding.recyclerView.layoutManager = GridLayoutManager(this@Unsplash, 3)
                        binding.recyclerView.adapter =
                            wallpaperAdapter(this, wallpapers) { imageUrl ->
                                setWallpaper(imageUrl)
                            }
                    } else {
                        Toast.makeText(this@Unsplash, "No wallpapers found", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("Unsplash", "Error fetching wallpapers", e)
                    Toast.makeText(this@Unsplash, "Failed to load wallpapers", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to set wallpaper
    fun setWallpaper(imageUrl: String) {
        val target = object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {
                try {
                    val wallpaperManager = android.app.WallpaperManager.getInstance(this@Unsplash)
                    wallpaperManager.setBitmap(bitmap)
                    Toast.makeText(this@Unsplash, "Wallpaper Set Successfully", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this@Unsplash, "Failed to set wallpaper", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                Toast.makeText(this@Unsplash, "Failed to load image", Toast.LENGTH_SHORT).show()
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                // Optional: you can display a loading placeholder here if needed
            }
        }
        // Load the image with Picasso and pass the target
        Picasso.get().load(imageUrl).into(target)
    }

    // Check for internet connectivity (Updated method for newer APIs)
    fun isInternetAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null && networkCapabilities.hasCapability(android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
