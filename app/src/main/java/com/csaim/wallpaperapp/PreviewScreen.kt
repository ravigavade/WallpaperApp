package com.csaim.wallpaperapp

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import android.app.WallpaperManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.csaim.wallpaperapp.databinding.ActivityPreviewScreenBinding
import com.squareup.picasso.Picasso

class PreviewScreen : AppCompatActivity() {

    private lateinit var binding:ActivityPreviewScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPreviewScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val link=intent.getStringExtra("WALLPAPER_URL")

        link?.let {
            Picasso.get()
                .load(it)
                .into(binding.imageView) // Use binding.imageView to refer to the ImageView
        }

        binding.setWallpaperBtn.setOnClickListener{
            if (link != null) {
                setWallpaper(link)
            }
        }



    }

    private fun setWallpaper(imageUrl: String) {
        val target = object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {
                try {
                    val wallpaperManager = WallpaperManager.getInstance(this@PreviewScreen)
                    wallpaperManager.setBitmap(bitmap)
                    Toast.makeText(
                        this@PreviewScreen,
                        "Wallpaper Set Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(
                        this@PreviewScreen,
                        "Failed to set wallpaper",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                Toast.makeText(this@PreviewScreen, "Failed to load image", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                // Optional: you can display a loading placeholder here if needed
            }
        }

        // Load the image with Picasso and pass the target
        Picasso.get().load(imageUrl).into(target)
    }
}