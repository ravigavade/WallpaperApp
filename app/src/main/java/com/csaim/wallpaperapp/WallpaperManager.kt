package com.csaim.wallpaperapp

import android.content.Context
import android.net.ConnectivityManager
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.getSystemService
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class WallpaperManager {
    private val okHttpClient: OkHttpClient

    init {
        val builder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(loggingInterceptor)
        okHttpClient = builder.build()
    }

    suspend fun retrieveCarsWallpaper(q: String,apikey:String): List<WallpaperData> = withContext(Dispatchers.IO) {
        val request = Request.Builder()
//            .url("https://wallhaven.cc/api/v1/search?q=$q&resolutions=1920x1200&categories=111*")
            .url("https://pixabay.com/api/?q=$q&orientation=vertical&key=$apikey&per_page=200&safesearch=true&image_type=photo,illustration&page=2")
//            .header("authorization","Bearer $apikey")
            .get()
            .build()

        val response: Response = okHttpClient.newCall(request).execute()
        val responseBody = response.body?.string()
        Log.d("responseBody","$responseBody")

        if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
            val wallpaperList = mutableListOf<WallpaperData>()

            val json = JSONObject(responseBody)
            val wallpapers = json.getJSONArray("hits")

            for (i in 0 until wallpapers.length()) {
//                val currentWallpaper = wallpapers.getJSONObject(i)
//                val category = currentWallpaper.optString("category", "Unknown")
//                val views = currentWallpaper.optString("views", "0")
//                val path = currentWallpaper.optString("path", "")

                val currentWallpaper=wallpapers.getJSONObject(i)
                val path=currentWallpaper.getString("largeImageURL")

                if (path.isNotEmpty()) {
                    val walp = WallpaperData(
//                        category = category,
//                        views = views,
                        path = path,
                    )

                    wallpaperList.add(walp)
                }
            }
            wallpaperList.shuffle()
            wallpaperList
        } else {
            emptyList()
        }
    }

    suspend fun retrieveIllWallpaper(q: String,apikey:String): List<WallpaperData> = withContext(Dispatchers.IO) {
        val request = Request.Builder()
//            .url("https://wallhaven.cc/api/v1/search?q=$q&resolutions=1920x1200&categories=111*")
            .url("https://pixabay.com/api/?orientation=vertical&key=$apikey&per_page=200&safesearch=true&image_type=$q&page=2")
//            .header("authorization","Bearer $apikey")
            .get()
            .build()

        val response: Response = okHttpClient.newCall(request).execute()
        val responseBody = response.body?.string()
        Log.d("responseBody","$responseBody")

        if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
            val wallpaperList = mutableListOf<WallpaperData>()

            val json = JSONObject(responseBody)
            val wallpapers = json.getJSONArray("hits")

            for (i in 0 until wallpapers.length()) {
//                val currentWallpaper = wallpapers.getJSONObject(i)
//                val category = currentWallpaper.optString("category", "Unknown")
//                val views = currentWallpaper.optString("views", "0")
//                val path = currentWallpaper.optString("path", "")

                val currentWallpaper=wallpapers.getJSONObject(i)
                val path=currentWallpaper.getString("largeImageURL")

                if (path.isNotEmpty()) {
                    val walp = WallpaperData(
//                        category = category,
//                        views = views,
                        path = path,
                    )

                    wallpaperList.add(walp)
                }
            }
            wallpaperList.shuffle()
            wallpaperList
        } else {
            emptyList()
        }
    }

    // Suspending function to fetch image URL based on category



    suspend fun retrieveAllWallpaper(apikey:String): List<WallpaperData> = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://pixabay.com/api/?orientation=vertical&key=$apikey&safesearch=true&per_page=200&image_type=photo,illustration&page=2")
//            .header("authorization","Bearer $apikey")
            .get()
            .build()

        val response: Response = okHttpClient.newCall(request).execute()
        val responseBody = response.body?.string()
        Log.d("responseBody","$responseBody")

        if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
            val wallpaperList = mutableListOf<WallpaperData>()

            val json = JSONObject(responseBody)
            val wallpapers = json.getJSONArray("hits")

            for (i in 0 until wallpapers.length()) {
//                val currentWallpaper = wallpapers.getJSONObject(i)
//                val category = currentWallpaper.optString("category", "Unknown")
//                val views = currentWallpaper.optString("views", "0")
//                val path = currentWallpaper.optString("path", "")

                val currentWallpaper=wallpapers.getJSONObject(i)
                val path=currentWallpaper.getString("largeImageURL")

                if (path.isNotEmpty()) {
                    val walp = WallpaperData(
//                        category = category,
//                        views = views,
                        path = path,
                    )

                    wallpaperList.add(walp)
                }
            }
            wallpaperList.shuffle()
            wallpaperList
        } else {
            emptyList()
        }
    }

    suspend fun retrieveCategory(category: String, apikey:String): List<WallpaperData> = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://pixabay.com/api/?category=$category&orientation=vertical&key=$apikey&safesearch=true&per_page=200&image_type=photo&page=2")
//            .header("authorization","Bearer $apikey")
            .get()
            .build()

        val response: Response = okHttpClient.newCall(request).execute()
        val responseBody = response.body?.string()
        Log.d("responseBody","$responseBody")

        if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
            val wallpaperList = mutableListOf<WallpaperData>()

            val json = JSONObject(responseBody)
            val wallpapers = json.getJSONArray("hits")

            for (i in 0 until wallpapers.length()) {
//
                val currentWallpaper=wallpapers.getJSONObject(i)
                val path=currentWallpaper.getString("largeImageURL")

                if (path.isNotEmpty()) {
                    val walp = WallpaperData(
//                        category = category,
//                        views = views,
                        path = path,
                    )
                    wallpaperList.add(walp)
                }
            }
            wallpaperList.shuffle()
            wallpaperList
        } else {
            emptyList()
        }
    }

    suspend fun Unsplash(): List<WallpaperData> = withContext(Dispatchers.IO) {
        try {
            val request = Request.Builder()
                .url("https://api.unsplash.com/photos/?client_id=ginxTLBDoVI_qDNjO2cMxtRp8fvswi4w2nwZasyGI0I&per_page=5")
                .build()

            Log.d("calling", "API called")

            val response: Response = okHttpClient.newCall(request).execute()
            val responseBody = response.body?.string()

            if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
                val wallpaperList = mutableListOf<WallpaperData>()
                val jsonArray = JSONArray(responseBody)

                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val urlsObject = jsonObject.optJSONObject("urls") // Using `optJSONObject` to avoid crashes
                    val fullUrl = urlsObject?.optString("full", "")

                    if (!fullUrl.isNullOrEmpty()) {
                        val wallpaper = WallpaperData(path = fullUrl)
                        wallpaperList.add(wallpaper)
                    }
                }

                Log.d("Unsplash", "Fetched ${wallpaperList.size} wallpapers")
                wallpaperList.shuffle()
                wallpaperList
            } else {
                Log.e("Unsplash", "Response unsuccessful: ${response.code}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("Unsplash", "Error fetching wallpapers", e)
            emptyList()
        }
    }








}
