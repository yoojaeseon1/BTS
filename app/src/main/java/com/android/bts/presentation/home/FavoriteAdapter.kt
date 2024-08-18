package com.android.bts.presentation.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.bts.databinding.RecyclerviewHotHolderBinding
import com.android.bts.presentation.search.ItemsEntity
import com.bumptech.glide.Glide

class FavoriteAdapter(private val videoClick: HotClickListener) :
    ListAdapter<ItemsEntity, RecyclerView.ViewHolder>(object :
        DiffUtil.ItemCallback<ItemsEntity>() {
        override fun areItemsTheSame(oldItem: ItemsEntity, newItem: ItemsEntity): Boolean {
//        return oldItem.snippet.thumbnail == newItem.snippet.thumbnail
//        return oldItem.id.videoId == newItem.id.videoId
            return oldItem.id.videoId == newItem.id.videoId
                    && oldItem.snippet.isLike == newItem.snippet.isLike
        }

        override fun areContentsTheSame(oldItem: ItemsEntity, newItem: ItemsEntity): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            RecyclerviewHotHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HotSpotHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val hotSpotHolder = holder as HotSpotHolder
        val currentItem = getItem(position)

        holder.binding.root.setOnLongClickListener {
            videoClick.onClickLike(currentItem, hotSpotHolder)
            true
        }

        holder.binding.root.setOnClickListener {
            videoClick.onClickDetail(currentItem, hotSpotHolder)
        }

        Glide.with(hotSpotHolder.thumbnail.context)
            .load(currentItem.snippet.thumbnail)
            .centerCrop()
            .into(hotSpotHolder.thumbnail)

        if (currentItem.snippet.isLike) {
            hotSpotHolder.like.visibility = ImageView.VISIBLE
        }

        hotSpotHolder.traveler.text = currentItem.snippet.channelTitle
        hotSpotHolder.title.text = currentItem.snippet.title

    }


    class HotSpotHolder(val binding: RecyclerviewHotHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var thumbnail = binding.searchRvHolderIvTitle
        var title = binding.searchRvHolderTvTitle
        var traveler = binding.searchRvHolderTvTraveler
        var like = binding.ivLike
    }
}