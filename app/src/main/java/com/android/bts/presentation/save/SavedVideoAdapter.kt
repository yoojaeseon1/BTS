package com.example.app.save

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.bts.databinding.RecyclerviewHotHolderBinding

class SavedVideoAdapter(private var savedVideos: List<SavedVideo>) :
    RecyclerView.Adapter<SavedVideoAdapter.SavedVideoViewHolder>() {

    inner class SavedVideoViewHolder(val binding: RecyclerviewHotHolderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedVideoViewHolder {
        val binding = RecyclerviewHotHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedVideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedVideoViewHolder, position: Int) {
        val video = savedVideos[position]
        holder.binding.searchRvHolderTvTitle.text = video.title
        holder.binding.searchRvHolderTvTraveler.text = video.description
    }

    override fun getItemCount(): Int = savedVideos.size

    fun updateSavedVideos(newVideos: List<SavedVideo>) {
        savedVideos = newVideos
        notifyDataSetChanged()
    }
}
