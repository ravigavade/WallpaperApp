package com.csaim.wallpaperapp

import android.app.DownloadManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import android.app.WallpaperManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.csaim.wallpaperapp.databinding.ActivityPreviewScreenBinding
import com.squareup.picasso.Picasso

class PreviewScreen : AppCompatActivity() {

    private lateinit var binding:ActivityPreviewScreenBinding
    private val favoriteWallpapers = mutableListOf<WallpaperData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPreviewScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the status bar color to black
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.black)

        // Optional: Change the status bar icons to be light (white) for better visibility on dark background
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false


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

        binding.downloadBtn.setOnClickListener {
            link?.let { imageUrl ->
                val fileName = "wallpaper_${System.currentTimeMillis()}" // Generate a unique file name
                downloadImage(this, imageUrl, fileName) // Call the download function
                Toast.makeText(this, "Downloaded Successful ", Toast.LENGTH_SHORT).show()
            } ?: run {
                Toast.makeText(this, "Failed to get wallpaper URL", Toast.LENGTH_SHORT).show()
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

    fun downloadImage(context: Context, imageUrl: String, fileName: String) {
        val request = DownloadManager.Request(Uri.parse(imageUrl))
            .setTitle(fileName)
            .setDescription("Downloading wallpaper...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "$fileName.jpg")
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }


}