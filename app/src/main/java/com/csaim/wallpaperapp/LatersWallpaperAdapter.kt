package com.csaim.wallpaperapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class LatersWallpaperAdapter(
    private val context: Context,
    val wallpapers:List<WallpaperData>,
    private val onWallpaperClick: (String) -> Unit // callback for wallpaper click
    ):RecyclerView.Adapter<LatersWallpaperAdapter.ViewHolder>() {

    class ViewHolder(rootLayout: View): RecyclerView.ViewHolder(rootLayout){
        val icon: ImageView =rootLayout.findViewById(R.id.icon)
//        val category:TextView=rootLayout.findViewById(R.id.category)
//        val views:TextView=rootLayout.findViewById(R.id.views)

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

//        er

        if(currentWallpapers.path.isNotEmpty()){
            Picasso.get().setIndicatorsEnabled(true)
            Picasso.get()
                .load(currentWallpapers.path)
                .into(holder.icon)
        }

        holder.icon.setOnClickListener {
            onWallpaperClick(currentWallpapers.path)
        }

    }
}