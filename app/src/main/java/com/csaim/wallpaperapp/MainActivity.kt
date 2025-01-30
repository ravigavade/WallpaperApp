package com.csaim.wallpaperapp

import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
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
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {


        private lateinit var binding:ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = ContextCompat.getColor(this, R.color.black)


        hideKeyboard()

        // Delay the rating prompt by 30 seconds (30000 ms)
        Handler(mainLooper).postDelayed({
            showRatingDialog()
        }, 30000) // 30 seconds delay


        binding=ActivityMain2Binding.inflate(layoutInflater)
        // Set a different XML layout file
        setContentView(binding.root)

        binding.unsplash.setOnClickListener {
            Toast.makeText(this, "Coming soon...", Toast.LENGTH_SHORT).show()
        }

        binding.drawer.setOnClickListener {
            Toast.makeText(this, "Coming soon...", Toast.LENGTH_SHORT).show()
        }

        //all colors btn
        binding.red.setOnClickListener {
            val color="red"
            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)

        }

        binding.blue.setOnClickListener {
            val color="blue"
            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)

        }
        binding.green.setOnClickListener {
            val color="green"
            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)

        }
        binding.yellow.setOnClickListener {
            val color="yellow"
            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)

        }
        binding.brown.setOnClickListener {
            val color="brown"
            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)

        }
        binding.black.setOnClickListener {
            val color="black"
            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)

        }
        binding.turquoise.setOnClickListener {
            val color="turquoise"
            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)

        }
        binding.lilac.setOnClickListener {
            val color="lilac"
            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)

        }
        binding.white.setOnClickListener {
            val color="white"
            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)

        }
        binding.pink.setOnClickListener {
            val color="pink"
            startActivity(Intent(this,wList::class.java).putExtra("Extra",color))
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)

        }



        //wlist intent for the illustration
        binding.allIllustration.setOnClickListener {
            val q="illustration"
            val intent=Intent(this, wList::class.java)
            intent.putExtra("Extra",q)  // Pass data to the next activity
            startActivity(intent)
            overridePendingTransition(R.transition.slide_up, R.transition.slide_down)
        }
        binding.allPhotos.setOnClickListener {
            val q="photo"
            val intent=Intent(this, wList::class.java)
            intent.putExtra("Extra",q)  // Pass data to the next activity
            startActivity(intent)
            overridePendingTransition(R.transition.slide_up, R.transition.slide_down)

        }

        binding.collection.setOnClickListener {
            val intent=Intent(this, AllWallpapersActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.transition.slide_up,android.R.anim.fade_out)

        }

        binding.backgrounds.setOnClickListener {
            val q="backgrounds"
            val intent=Intent(this, CategoryViewer::class.java)
            intent.putExtra("Extra",q)  // Pass data to the next activity
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        binding.nature.setOnClickListener {
            val q="nature"
            val intent=Intent(this, CategoryViewer::class.java)
            intent.putExtra("Extra",q)  // Pass data to the next activity
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        binding.animals.setOnClickListener {
            val q="animals"
            val intent=Intent(this, CategoryViewer::class.java)
            intent.putExtra("Extra",q)  // Pass data to the next activity
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        binding.travel.setOnClickListener {
            val q="travel"
            val intent=Intent(this, CategoryViewer::class.java)
            intent.putExtra("Extra",q)  // Pass data to the next activity
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        binding.buildings.setOnClickListener {
            val q="buildings"
            val intent=Intent(this, CategoryViewer::class.java)
            intent.putExtra("Extra",q)  // Pass data to the next activity
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        binding.computer.setOnClickListener {
            val q="computer"
            val intent=Intent(this, CategoryViewer::class.java)
            intent.putExtra("Extra",q)  // Pass data to the next activity
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }




        binding.keyword.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)) {

                val q = binding.keyword.text.toString().trim()
                if (q.isNotEmpty()) {
                    val intent = Intent(this@MainActivity, CategoryViewer::class.java)
                    intent.putExtra("Extra", q)
                    startActivity(intent)
                    overridePendingTransition(R.transition.zoom_in,android.R.anim.slide_out_right)

                    binding.keyword.setText("") // Clears the EditText

                } else {
                    Toast.makeText(this, "Please enter a keyword", Toast.LENGTH_SHORT).show()
                }
                return@setOnEditorActionListener true // Event is handled
            }
            false // Let system handle other actions
        }






        // URL to fetch the random image
        val imageUrl = "https://picsum.photos/1080/1920"

        // Use Picasso to load and display the image
                Picasso.get()
                    .load(imageUrl)
                    .into(binding.randomImg)

        binding.imgholder.visibility = View.VISIBLE

//         Set click listener for downloading the image
                binding.randomImg.setOnClickListener {
                    Toast.makeText(this, "Downloading random pic\uD83E\uDD2A", Toast.LENGTH_SHORT).show()
                    val fileName = "Pixlr_${System.currentTimeMillis()}.jpg" // Unique file name
                    downloadImage(this, imageUrl, fileName)
        }

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


    private fun showRatingDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Enjoying the App?")
        builder.setMessage("If you like the app, please consider rating it! It helps us a lot.")

        builder.setPositiveButton("Yes") { _, _ ->
            openPlayStore() // Redirect to Play Store if user clicks "Yes"
        }

        builder.setNegativeButton("Maybe Later") { dialog, _ ->
            dialog.dismiss() // Dismiss the dialog if user clicks "Maybe Later"
        }

        builder.show() // Show the dialog
    }

    private fun openPlayStore() {
        val appPackageName = packageName // Get your app's package name
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName"))
            startActivity(intent)
        } catch (e: Exception) {
            // If Play Store app is not available, open in browser
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName"))
            startActivity(intent)
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
        fun hideKeyboard() {
            val view = this.currentFocus
            if (view != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }


}
