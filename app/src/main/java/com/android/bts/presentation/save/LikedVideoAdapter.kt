package com.android.bts.presentation.save

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.bts.R
import com.bumptech.glide.Glide

class LikedVideoAdapter(
    private var likedVideos: List<LikedVideo>
) : RecyclerView.Adapter<LikedVideoAdapter.LikedVideoViewHolder>() {

    class LikedVideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.search_rv_holder_tv_title)
        val descriptionTextView: TextView = itemView.findViewById(R.id.search_rv_holder_tv_traveler)
        val thumbnail: ImageView = itemView.findViewById(R.id.search_rv_holder_iv_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikedVideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_hot_holder, parent, false)
        return LikedVideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: LikedVideoViewHolder, position: Int) {
        val video = likedVideos[position]
        holder.titleTextView.text = video.title
        holder.descriptionTextView.text = video.channelTitle

        Glide.with(holder.thumbnail.context)
            .load(video.thumbnailUrl)
            .centerCrop()
            .into(holder.thumbnail)

    }

    override fun getItemCount(): Int = likedVideos.size

    fun updateLikedVideos(newVideos: List<LikedVideo>) {
        likedVideos = newVideos
        notifyDataSetChanged()
    }
}
