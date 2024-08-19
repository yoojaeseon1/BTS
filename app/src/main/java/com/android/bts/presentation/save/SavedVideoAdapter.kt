package com.example.app.save

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.bts.R

class SavedVideoAdapter(
    private var savedVideos: List<SavedVideo>,
    private val onVideoClick: (SavedVideo) -> Unit
) : RecyclerView.Adapter<SavedVideoAdapter.SavedVideoViewHolder>() {

    inner class SavedVideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.search_rv_holder_tv_title)
        val descriptionTextView: TextView = itemView.findViewById(R.id.search_rv_holder_tv_traveler)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedVideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_hot_holder, parent, false)
        return SavedVideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavedVideoViewHolder, position: Int) {
        val video = savedVideos[position]
        holder.titleTextView.text = video.title
        holder.descriptionTextView.text = video.description

        holder.itemView.setOnClickListener {
            onVideoClick(video)
        }
    }

    override fun getItemCount(): Int = savedVideos.size

    fun updateSavedVideos(newVideos: List<SavedVideo>) {
        savedVideos = newVideos
        notifyDataSetChanged()
    }
}

