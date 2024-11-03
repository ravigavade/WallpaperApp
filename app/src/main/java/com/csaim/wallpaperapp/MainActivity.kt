package com.csaim.wallpaperapp

import android.app.WallpaperManager
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
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.csaim.wallpaperapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val wallpaperManagerLatest = LatestWallpaperManager()
//        val wallpaperManagerIllustraion = IllustrationWallpaperManager()
//        var wallpapers = listOf<WallpaperData>()
//
//        val apiKey=getString(com.csaim.wallpaperapp.R.string.apiKey)



        //btn for cars banner
//        binding.carsBanner.setOnClickListener {
//            val carsBanner="cars"
//            startActivity(Intent(this,wList::class.java).putExtra("Extra",carsBanner))
//        }
//         //btn for anime banner
//        binding.animeBanner.setOnClickListener {
//            val animeBanner="anime"
//            startActivity(Intent(this,wList::class.java).putExtra("Extra",animeBanner))
//
//        }

        //btn going to seaerch activity
//        binding.searchButton.setOnClickListener{
//            startActivity(Intent(this,SearchWallpaper::class.java))
//        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Set default fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MainFragment())
                .commit()
        }
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            val selectedFragment = when (item.itemId) {
                R.id.nav_main -> MainFragment()
                R.id.nav_search -> SearchFragment()
//                R.id.nav_account -> AccountFragment()
                else -> null
            }
            selectedFragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, it)
                    .commit()
                true
            } ?: false
        }




//        //all colors btn
//        binding.red.setOnClickListener {
//            val color="red"
//            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
//        }
//        binding.blue.setOnClickListener {
//            val color="blue"
//            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
//        }
//        binding.green.setOnClickListener {
//            val color="green"
//            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
//        }
//        binding.yellow.setOnClickListener {
//            val color="yellow"
//            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
//        }
//        binding.brown.setOnClickListener {
//            val color="brown"
//            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
//        }
//        binding.black.setOnClickListener {
//            val color="black"
//            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
//        }
//        binding.turquoise.setOnClickListener {
//            val color="turquoise"
//            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
//        }
//        binding.lilac.setOnClickListener {
//            val color="lilac"
//            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
//        }
//        binding.transparent.setOnClickListener {
//            val color="transparent"
//            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
//        }
//        binding.grayScale.setOnClickListener {
//            val color="grayScale"
//            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
//        }


        //layout for popular wallpaper horizontal view
//        lifecycleScope.launch {
//            withContext(Dispatchers.IO) {
//                wallpapers = wallpaperManagerLatest.retrieveLatestsWallpaper(apiKey)
//                Log.d("fucal","calling")
//            }
//
//            binding.LatestWallpaperRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
//            binding.LatestWallpaperRecyclerView.adapter = LatersWallpaperAdapter(this@MainActivity, wallpapers) { imageUrl ->
//                setWallpaper(imageUrl)
//            }
//
//        }
//        //layout for Illustration wallpaper horizontal view
//        lifecycleScope.launch {
//            withContext(Dispatchers.IO) {
//                wallpapers = wallpaperManagerIllustraion.retrieveIllustrationWallpaper(apiKey)
//                Log.d("fucal","calling")
//            }
//
//            binding.IllustrationRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
//            binding.IllustrationRecyclerView.adapter = LatersWallpaperAdapter(this@MainActivity, wallpapers) { imageUrl ->
//                setWallpaper(imageUrl)
//            }
//
//        }




    }

    //function that sets the image as wallpaper
//    private fun setWallpaper(imageUrl: String) {
//        val target = object : com.squareup.picasso.Target {
//            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {
//                try {
//                    val wallpaperManager = WallpaperManager.getInstance(this@MainActivity)
//                    wallpaperManager.setBitmap(bitmap)
//                    Toast.makeText(this@MainActivity, "Wallpaper Set Successfully", Toast.LENGTH_SHORT).show()
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                    Toast.makeText(this@MainActivity, "Failed to set wallpaper", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
//                Toast.makeText(this@MainActivity, "Failed to load image", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
//                // Optional: you can display a loading placeholder here if needed
//            }
//        }
//
//        // Load the image with Picasso and pass the target
//        Picasso.get().load(imageUrl).into(target)
//    }
}