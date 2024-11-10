package com.csaim.wallpaperapp

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject

class PhotosWallpaperManager {
    private val okHttpClient: OkHttpClient

    init {
        val builder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(loggingInterceptor)
        okHttpClient = builder.build()
    }


    //not latest but vecotr
    suspend fun retrievePhotoWallpaper(apikey:String): List<WallpaperData> = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://pixabay.com/api/?key=$apikey&orientation=vertical&order=latest&image_type=photo&per_page=200&safesearch=true")
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

                val currentWallpaper=wallpapers.getJSONObject(i)
                val path=currentWallpaper.getString("largeImageURL")

                if (path.isNotEmpty()) {
                    val walp = WallpaperData(
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
