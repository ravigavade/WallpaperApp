package com.csaim.wallpaperapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class PhotosWallpaperAdapter(
    private val context: Context,
    val wallpapers:List<WallpaperData>,
    private val onWallpaperClick: (String)->Unit
    ):RecyclerView.Adapter<PhotosWallpaperAdapter.ViewHolder>() {

    class ViewHolder(rootLayout: View): RecyclerView.ViewHolder(rootLayout){
        val icon: ImageView =rootLayout.findViewById(R.id.icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val rootLayout: View =layoutInflater.inflate(R.layout.latest_item_layout, parent, false)
        val viewHolder=ViewHolder(rootLayout)
        return viewHolder

    }

    override fun getItemCount(): Int {
        return wallpapers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentWallpapers=wallpapers[position]

        if(currentWallpapers.path.isNotEmpty()){
//            Picasso.get().setIndicatorsEnabled(true)
            Picasso.get()
                .load(currentWallpapers.path)
                .into(holder.icon)
        }

//        old btn wid click
        holder.icon.setOnClickListener {
            //sets the pic as wallpapaer
            onWallpaperClick(currentWallpapers.path)
        }


        holder.icon.setOnClickListener {
            val intent=Intent(context, PreviewScreen::class.java).apply {
                putExtra("WALLPAPER_URL", currentWallpapers.path)
            }
            context.startActivity(intent)
        }

    }
}