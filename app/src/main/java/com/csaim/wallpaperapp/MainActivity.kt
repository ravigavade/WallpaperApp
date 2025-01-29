package com.csaim.wallpaperapp

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import com.csaim.wallpaperapp.NetworkUtils.isInternetAvailable
import com.csaim.wallpaperapp.databinding.ActivityMain2Binding
import com.csaim.wallpaperapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
        private lateinit var binding:ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)





        binding=ActivityMain2Binding.inflate(layoutInflater)
        // Set a different XML layout file
        setContentView(binding.root)

        //all colors btn
        binding.red.setOnClickListener {
            val color="red"
            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
        }
        binding.blue.setOnClickListener {
            val color="blue"
            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
        }
        binding.green.setOnClickListener {
            val color="green"
            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
        }
        binding.yellow.setOnClickListener {
            val color="yellow"
            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
        }
        binding.brown.setOnClickListener {
            val color="brown"
            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
        }
        binding.black.setOnClickListener {
            val color="black"
            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
        }
        binding.turquoise.setOnClickListener {
            val color="turquoise"
            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
        }
        binding.lilac.setOnClickListener {
            val color="lilac"
            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
        }
        binding.transparent.setOnClickListener {
            val color="transparent"
            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
        }
        binding.grayScale.setOnClickListener {
            val color="grayScale"
            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
        }

        //wlist intent for the illustration
        binding.allIllustration.setOnClickListener {
            val q="illustration"
            val intent=Intent(this, wList::class.java)
            intent.putExtra("Extra",q)  // Pass data to the next activity
            startActivity(intent)
        }
        binding.allPhotos.setOnClickListener {
            val q="photo"
            val intent=Intent(this, wList::class.java)
            intent.putExtra("Extra",q)  // Pass data to the next activity
            startActivity(intent)
        }

        binding.collection.setOnClickListener {
            val intent=Intent(this, AllWallpapersActivity::class.java)
            startActivity(intent)
        }

        binding.backgrounds.setOnClickListener {
            val q="backgrounds"
            val intent=Intent(this, CategoryViewer::class.java)
            intent.putExtra("Extra",q)  // Pass data to the next activity
            startActivity(intent)
        }
        binding.nature.setOnClickListener {
            val q="nature"
            val intent=Intent(this, CategoryViewer::class.java)
            intent.putExtra("Extra",q)  // Pass data to the next activity
            startActivity(intent)
        }
        binding.animals.setOnClickListener {
            val q="animals"
            val intent=Intent(this, CategoryViewer::class.java)
            intent.putExtra("Extra",q)  // Pass data to the next activity
            startActivity(intent)
        }
        binding.travel.setOnClickListener {
            val q="travel"
            val intent=Intent(this, CategoryViewer::class.java)
            intent.putExtra("Extra",q)  // Pass data to the next activity
            startActivity(intent)
        }
        binding.buildings.setOnClickListener {
            val q="buildings"
            val intent=Intent(this, CategoryViewer::class.java)
            intent.putExtra("Extra",q)  // Pass data to the next activity
            startActivity(intent)
        }
        binding.computer.setOnClickListener {
            val q="computer"
            val intent=Intent(this, CategoryViewer::class.java)
            intent.putExtra("Extra",q)  // Pass data to the next activity
            startActivity(intent)
        }



        binding.keyword.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                // Get the text from the EditText
                val q = binding.keyword.text.toString().trim()
                val intent=Intent(this, CategoryViewer::class.java)
                intent.putExtra("Extra",q)  // Pass data to the next activity
                startActivity(intent)
                // Hide the keyboard after searching
//                hideKeyboard()
                true  // Return true to indicate the event is handled
            } else {
                false  // Let the system handle other actions
            }
        }








        // URL to fetch the random image
        val imageUrl = "https://picsum.photos/1920/1080"

        // Use Picasso to load and display the image
        Picasso.get()
            .load(imageUrl)  // The image URL
            .into(binding.randomImg)  // ImageView to display the image

        // Set click listener for downloading the image
//        binding.randomImg.setOnClickListener {
//            val fileName = "wallpaper_${System.currentTimeMillis()}" // Unique file name
//            downloadImage(this, imageUrl, fileName) // Call the download function
//            Toast.makeText(this, "Download Started", Toast.LENGTH_SHORT).show()
//        }
    }

    // Function to download image
    fun downloadImage(context: Context, imageUrl: String, fileName: String) {
        try {
            val request = DownloadManager.Request(Uri.parse(imageUrl))
                .setTitle(fileName)
                .setDescription("Downloading wallpaper...")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "$fileName.jpg")

            // Get the system's download manager and enqueue the request
            val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadManager.enqueue(request)

            // Optionally, display a message or notification for download progress
        } catch (e: Exception) {
            // Handle errors if any (e.g., permission issues)
            Toast.makeText(context, "Download failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }












        /* Commenting out the previous implementation
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!NetworkUtils.isInternetAvailable(this)) {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show()
        } else {
            window.statusBarColor = ContextCompat.getColor(this, android.R.color.black)
            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false

            val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
            bottomNavigationView.setBackgroundColor(ContextCompat.getColor(this, R.color.bg))

            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, MainFragment())
                    .commit()
            }
            bottomNavigationView.setOnNavigationItemSelectedListener { item ->
                val selectedFragment = when (item.itemId) {
                    R.id.nav_main -> MainFragment()
                    R.id.nav_search -> SearchFragment()
                    R.id.nav_account -> AllWallpapersFragment()
                    else -> null
                }
                selectedFragment?.let {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, it)
                        .commit()
                    true
                } ?: false
            }
        }
        */
}
