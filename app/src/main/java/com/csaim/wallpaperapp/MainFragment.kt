package com.csaim.wallpaperapp

import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.csaim.wallpaperapp.databinding.FragmentMainBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //here we go

        val wallpaperManagerLatest = PhotosWallpaperManager()
        val wallpaperManagerIllustraion = IllustrationWallpaperManager()
        var wallpapers = listOf<WallpaperData>()

        val apiKey=getString(com.csaim.wallpaperapp.R.string.apiKey)

        //all colors btn
        binding.red.setOnClickListener {
            val color="red"
            startActivity(Intent(requireContext(),wList::class.java).putExtra("Extra",color))
        }
        binding.blue.setOnClickListener {
            val color="blue"
            startActivity(Intent(requireContext(),wList::class.java).putExtra("Extra",color))
        }
        binding.green.setOnClickListener {
            val color="green"
            startActivity(Intent(requireContext(),wList::class.java).putExtra("Extra",color))
        }
        binding.yellow.setOnClickListener {
            val color="yellow"
            startActivity(Intent(requireContext(),wList::class.java).putExtra("Extra",color))
        }
        binding.brown.setOnClickListener {
            val color="brown"
            startActivity(Intent(requireContext(),wList::class.java).putExtra("Extra",color))
        }
        binding.black.setOnClickListener {
            val color="black"
            startActivity(Intent(requireContext(),wList::class.java).putExtra("Extra",color))
        }
        binding.turquoise.setOnClickListener {
            val color="turquoise"
            startActivity(Intent(requireContext(),wList::class.java).putExtra("Extra",color))
        }
        binding.lilac.setOnClickListener {
            val color="lilac"
            startActivity(Intent(requireContext(),wList::class.java).putExtra("Extra",color))
        }
        binding.transparent.setOnClickListener {
            val color="transparent"
            startActivity(Intent(requireContext(),wList::class.java).putExtra("Extra",color))
        }
        binding.grayScale.setOnClickListener {
            val color="grayScale"
            startActivity(Intent(requireContext(),wList::class.java).putExtra("Extra",color))
        }

        //layout for popular latest horizontal view
        lifecycleScope.launch {
            val wallpapersLatest: List<WallpaperData> = withContext(Dispatchers.IO) {
                wallpaperManagerLatest.retrievePhotoWallpaper(apiKey)
            }

            binding.LatestWallpaperRecyclerView.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            binding.LatestWallpaperRecyclerView.adapter = PhotosWallpaperAdapter(requireContext(), wallpapersLatest) { imageUrl ->
                setWallpaper(imageUrl)
            }
        }


        //wlist intent for the illustration
        binding.allIllustration.setOnClickListener {
            val q = "illustration"
            val intent = Intent(requireContext(), wList::class.java)
            intent.putExtra("Extra", q)  // Pass data to the next activity
            startActivity(intent)
        }
        binding.allPhotos.setOnClickListener {
            val q = "photo"
            val intent = Intent(requireContext(), wList::class.java)
            intent.putExtra("Extra", q)  // Pass data to the next activity
            startActivity(intent)
        }

//layout for illustration wallpaper horizontal view
        //checking if internet is available hahahhaha bc
        if(isInternetAvailable()) {
            lifecycleScope.launch {
                val wallpapersIllustration: List<WallpaperData> = withContext(Dispatchers.IO) {
                    wallpaperManagerIllustraion.retrieveIllustrationWallpaper(apiKey)
                }

                binding.IllustrationRecyclerView.layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                binding.IllustrationRecyclerView.adapter =
                    PhotosWallpaperAdapter(requireContext(), wallpapersIllustration) { imageUrl ->
                        setWallpaper(imageUrl)
                    }
            }
        }else{
            Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show()
        }




    }

    private fun setWallpaper(imageUrl: String) {
        val target = object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {
                try {
                    val wallpaperManager = WallpaperManager.getInstance(requireContext())
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


    fun isInternetAvailable(): Boolean {
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }

}