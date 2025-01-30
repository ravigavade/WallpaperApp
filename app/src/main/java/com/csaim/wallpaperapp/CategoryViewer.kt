package com.csaim.wallpaperapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.csaim.wallpaperapp.databinding.ActivityCategoryManagerBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryViewer : AppCompatActivity() {
    private lateinit var binding:ActivityCategoryManagerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCategoryManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //transitions on back
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish() // Close activity
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        })

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
                    if (q == "nature") {
                        // Fetch wallpapers for the category (illustration/photo)
                        wallpapers = wallpaperManager.retrieveCategory(q, apiKey)
                    }else if (q=="background"){
                        wallpapers = wallpaperManager.retrieveCategory(q, apiKey)
                    }else if (q=="animals"){
                        wallpapers = wallpaperManager.retrieveCategory(q, apiKey)
                    }else if (q=="travel"){
                        wallpapers = wallpaperManager.retrieveCategory(q, apiKey)
                    }else if (q=="buildings"){
                        wallpapers = wallpaperManager.retrieveCategory(q, apiKey)
                    }else if (q=="computer"){
                        wallpapers = wallpaperManager.retrieveCategory(q, apiKey)
                    } else {
                        // Fetch wallpapers based on color or other categories (using the value from q)
                        wallpapers = wallpaperManager.retrieveCarsWallpaper(q, apiKey)
                    }
                    Log.d("Wallpaper", "Fetched ${wallpapers.size} wallpapers")
                }

                binding.recyclerView.layoutManager = GridLayoutManager(this@CategoryViewer, 3)
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
                    val wallpaperManager = android.app.WallpaperManager.getInstance(this@CategoryViewer)
                    wallpaperManager.setBitmap(bitmap)
                    Toast.makeText(this@CategoryViewer, "Wallpaper Set Successfully", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this@CategoryViewer, "Failed to set wallpaper", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                Toast.makeText(this@CategoryViewer, "Failed to load image", Toast.LENGTH_SHORT).show()
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

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        overridePendingTransition(R.transition.stay, R.transition.slide_down)
        super.onBackPressed()
    }




}