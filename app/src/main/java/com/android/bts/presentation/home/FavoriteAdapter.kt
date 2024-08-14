package com.android.bts.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.bts.databinding.RecyclerviewHotHolderBinding
import com.android.bts.databinding.RecyclerviewSearchHolderBinding
import com.android.bts.presentation.search.SnippetEntity
import com.bumptech.glide.Glide

class FavoriteAdapter(private val onClick: () -> Unit) : ListAdapter<SnippetEntity, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<SnippetEntity>(){
    override fun areItemsTheSame(oldItem: SnippetEntity, newItem: SnippetEntity): Boolean {
        return oldItem.thumbnail == newItem.thumbnail
    }

    override fun areContentsTheSame(oldItem: SnippetEntity, newItem: SnippetEntity): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RecyclerviewHotHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HotSpotHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val interestedHolder = holder as HotSpotHolder
        val currentItem = getItem(position)

        Glide.with(interestedHolder.thumbnail.context)
            .load(currentItem.thumbnail)
            .centerCrop()
            .into(interestedHolder.thumbnail)

        interestedHolder.traveler.text = currentItem.channelTitle
        interestedHolder.title.text = currentItem.title

    }


    class HotSpotHolder(val binding: RecyclerviewHotHolderBinding): RecyclerView.ViewHolder(binding.root) {
        var thumbnail = binding.searchRvHolderIvTitle
        var title = binding.searchRvHolderTvTitle
        var traveler = binding.searchRvHolderTvTraveler
    }
}