package com.csaim.wallpaperapp

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

    suspend fun retrieveCarsWallpaper(q: String): List<WallpaperData> = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://wallhaven.cc/api/v1/search?q=$q&resolutions=1920x1200&categories=111*")
//            .url("https://pixabay.com/api/?q=cars&image_type=photo&category=computer&min_width=1080&min_height=1920&orientation=vertical")
//            .header("authorization","Bearer $apikey")
            .get()
            .build()

        val response: Response = okHttpClient.newCall(request).execute()
        val responseBody = response.body?.string()

        if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
            val wallpaperList = mutableListOf<WallpaperData>()
            val json = JSONObject(responseBody)
            val wallpapers = json.getJSONArray("data")

            for (i in 0 until wallpapers.length()) {
                val currentWallpaper = wallpapers.getJSONObject(i)
                val category = currentWallpaper.optString("category", "Unknown")
                val views = currentWallpaper.optString("views", "0")
                val path = currentWallpaper.optString("path", "")

                if (path.isNotEmpty()) {
                    val walp = WallpaperData(
                        category = category,
                        views = views,
                        path = path,
                    )
                    wallpaperList.add(walp)
                }
            }
            wallpaperList
        } else {
            emptyList()
        }
    }
}
