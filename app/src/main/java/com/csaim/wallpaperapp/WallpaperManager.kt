package com.csaim.wallpaperapp

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject

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
            .url("https://pixabay.com/api/?q=$q&orientation=vertical&key=$apikey&per_page=200&safesearch=true")
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

    suspend fun retrieveCategoryWallpaper(category: String,apikey:String): List<WallpaperData> = withContext(Dispatchers.IO) {
        val request = Request.Builder()
//            .url("https://wallhaven.cc/api/v1/search?q=$q&resolutions=1920x1200&categories=111*")
            .url("https://pixabay.com/api/?image_type=$category&orientation=vertical&key=$apikey&per_page=200&safesearch=true")
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


    suspend fun retrieveLimitedWallpaper(apikey:String): List<WallpaperData> = withContext(Dispatchers.IO) {
        val request = Request.Builder()
//            .url("https://wallhaven.cc/api/v1/search?q=$q&resolutions=1920x1200&categories=111*")
            .url("https://pixabay.com/api/?orientation=vertical&key=$apikey&safesearch=true")
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


    suspend fun retrieveAllWallpaper(apikey:String): List<WallpaperData> = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://pixabay.com/api/?orientation=vertical&key=$apikey&safesearch=true&per_page=200")
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







}
