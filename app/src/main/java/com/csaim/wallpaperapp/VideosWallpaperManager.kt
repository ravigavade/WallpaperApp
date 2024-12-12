package com.csaim.wallpaperapp

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject

class VideosWallpaperManager {
    private val okHttpClient: OkHttpClient

    init {
        val builder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(loggingInterceptor)
        okHttpClient = builder.build()
    }


    //not latest but vecotr
    suspend fun retrieveVideoWallpaper(apiKey: String): List<WallpaperData> = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://pixabay.com/api/videos/?key=$apiKey&min_width=1080&min_height=1920")
            .get()
            .build()

        val response: Response = okHttpClient.newCall(request).execute()
        val responseBody = response.body?.string()
        Log.d("responseBody", "$responseBody")

        if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
            val wallpaperList = mutableListOf<WallpaperData>()

            val json = JSONObject(responseBody)
            val wallpapers = json.getJSONArray("hits")

            for (i in 0 until wallpapers.length()) {
                val currentWallpaper = wallpapers.getJSONObject(i)
                val videos = currentWallpaper.getJSONObject("videos")
                val largeVideo = videos.getJSONObject("large")
                val videoUrl = largeVideo.getString("url") // Get the large video URL
                val thumbnailUrl = largeVideo.getString("thumbnail") // Get the thumbnail URL if needed

                if (videoUrl.isNotEmpty()) {
                    val walp = WallpaperData(
                        path = videoUrl, // Use video URL
                        thumbnail = thumbnailUrl // Optional: add thumbnail if required
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
