package com.android.bts.presentation.save

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.bts.R
import com.android.bts.databinding.RecyclerviewInterestedHolderBinding
import com.example.app.save.LikedVideo
import com.example.app.save.SavedVideo
import com.example.app.save.SavedVideoAdapter

class LikedVideoAdapter(
    private var likedVideos: List<LikedVideo>,
    private val onVideoClick: (LikedVideo) -> Unit
) : RecyclerView.Adapter<LikedVideoAdapter.LikedVideoViewHolder>() {

    inner class LikedVideoViewHolder(private val binding: RecyclerviewInterestedHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(video: LikedVideo) {
            binding.interestedRvHolderTvTitle.text = video.title
            binding.interestedRvHolderTvTraveler.text = video.description

            binding.root.setOnClickListener {
                onVideoClick(video)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikedVideoViewHolder {
        val binding =
            RecyclerviewInterestedHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return LikedVideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LikedVideoViewHolder, position: Int) {
        val video = likedVideos[position]
        holder.bind(video)
    }

    override fun getItemCount(): Int = likedVideos.size

    fun updateLikedVideos(newVideos: List<LikedVideo>) {
        likedVideos = newVideos
        notifyDataSetChanged()
    }
}
