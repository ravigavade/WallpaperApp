package com.csaim.wallpaperapp

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.csaim.wallpaperapp.databinding.FragmentSearchBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Access views with binding
        val wallpaperManager = WallpaperManager()
        var wallpapers = listOf<WallpaperData>()

        val apiKey=getString(com.csaim.wallpaperapp.R.string.apiKey)
        var q="nature"

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                wallpapers = wallpaperManager.retrieveCarsWallpaper(q, apiKey)
                Log.d("fucal", "calling")
            }
            binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
            binding.recyclerView.adapter = wallpaperAdapter(requireContext(), wallpapers) { imageUrl ->
                setWallpaper(imageUrl)
            }
        }

        binding.findBtn.setOnClickListener{
            val search=binding.keyword.getText().toString().trim()


            q=binding.keyword.getText().toString().trim()

            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    wallpapers = wallpaperManager.retrieveCarsWallpaper(q,apiKey)
                    Log.d("fucal","calling")
                }

                binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
                binding.recyclerView.adapter = wallpaperAdapter(requireContext(), wallpapers) { imageUrl ->
                    setWallpaper(imageUrl)
                }

            }
        }


    }

    fun setWallpaper(imageUrl: String) {
        val target = object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {
                try {
                    val wallpaperManager = android.app.WallpaperManager.getInstance(requireContext())
                    wallpaperManager.setBitmap(bitmap)
                    Toast.makeText(requireContext(), "Wallpaper Set Successfully", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Failed to set wallpaper", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                Toast.makeText(requireContext(), "Failed to load image", Toast.LENGTH_SHORT).show()
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                // Optional: you can display a loading placeholder here if needed
            }
        }

        // Load the image with Picasso and pass the target
        Picasso.get().load(imageUrl).into(target)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
