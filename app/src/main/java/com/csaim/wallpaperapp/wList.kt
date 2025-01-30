package com.csaim.wallpaperapp

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import android.app.WallpaperManager
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.csaim.wallpaperapp.databinding.ActivityWlistBinding
import com.squareup.picasso.Picasso
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

        val q = intent.getStringExtra("Extra") ?: "Default Value"
        val apiKey=getString(com.csaim.wallpaperapp.R.string.apiKey)
        Log.d("q value","value $q")

        window.statusBarColor = ContextCompat.getColor(this, android.R.color.black)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false

        if (isInternetAvailable()) {
            lifecycleScope.launch {
                // Fetch wallpapers based on the value of q
                withContext(Dispatchers.IO) {
                    if (q == "illustration" || q == "photo") {
                        // Fetch wallpapers for the category (illustration/photo)
                        wallpapers = wallpaperManager.retrieveCarsWallpaper(q, apiKey)
                    } else {
                        // Fetch wallpapers based on color or other categories (using the value from q)
                        wallpapers = wallpaperManager.retrieveCarsWallpaper(q, apiKey)
                    }
                    Log.d("Wallpaper", "Fetched ${wallpapers.size} wallpapers")
                }

                // Set up RecyclerView with GridLayoutManager and adapter
                binding.recyclerView.layoutManager = GridLayoutManager(this@wList, 3)
                binding.recyclerView.adapter =
                    wallpaperAdapter(this, wallpapers) { imageUrl ->
                        setWallpaper(imageUrl)
                    }
            }
        }else{
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
        }
    }

    //function that sets the image as wallpaper
    private fun setWallpaper(imageUrl: String) {
        val target = object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {
                try {
                    val wallpaperManager = WallpaperManager.getInstance(this@wList)
                    wallpaperManager.setBitmap(bitmap)
                    Toast.makeText(this@wList, "Wallpaper Set Successfully", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this@wList, "Failed to set wallpaper", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                Toast.makeText(this@wList, "Failed to load image", Toast.LENGTH_SHORT).show()
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                // Optional: you can display a loading placeholder here if needed
            }
        }

        // load the image with Picasso and pass the target
        Picasso.get().load(imageUrl).into(target)
    }

    fun isInternetAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }



}