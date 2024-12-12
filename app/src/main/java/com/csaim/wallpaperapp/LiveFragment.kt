package com.csaim.wallpaperapp

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.csaim.wallpaperapp.NetworkUtils.isInternetAvailable
import com.csaim.wallpaperapp.databinding.FragmentLiveBinding
import com.csaim.wallpaperapp.databinding.FragmentMainBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LiveFragment : Fragment() {


    private var _binding: FragmentLiveBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wallpaperManagerLatest=VideosWallpaperManager()
        var wallpapers = listOf<WallpaperData>()
        val apiKey=getString(com.csaim.wallpaperapp.R.string.apiKey)


        lifecycleScope.launch {
            val wallpapersLatest: List<WallpaperData> = withContext(Dispatchers.IO) {
                wallpaperManagerLatest.retrieveVideoWallpaper(apiKey)
            }

            binding.LatestWallpaperRecyclerView.layoutManager = LinearLayoutManager(
                requireContext(),
//                LinearLayoutManager.HORIZONTAL,
//                false
            )
            binding.LatestWallpaperRecyclerView.adapter = VideowallpaperAdapter(requireContext(), wallpapersLatest) { imageUrl ->
                setWallpaper(imageUrl)
            }
        }

    }

    private fun setWallpaper(imageUrl: String) {
        val target=object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {
                try {
                    val wallpaperManager= WallpaperManager.getInstance(requireContext())
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
            }
        }

        //loading the image
        Picasso.get().load(imageUrl).into(target)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }



}