package com.csaim.wallpaperapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.csaim.wallpaperapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.carsBanner.setOnClickListener {
            val carsBanner="cars"
            startActivity(Intent(this,wList::class.java).putExtra("Extra",carsBanner))
        }

        binding.animeBanner.setOnClickListener {
            val animeBanner="anime"
            startActivity(Intent(this,wList::class.java).putExtra("Extra",animeBanner))

        }

//        binding.findBtn.setOnClickListener{
//            val search=binding.keyword.getText().toString().trim()
//            startActivity(Intent(this,wList::class.java).putExtra("Extra",search))
//        }

    }
}