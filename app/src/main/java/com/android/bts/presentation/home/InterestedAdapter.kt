package com.android.bts.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.bts.databinding.RecyclerviewInterestedHolderBinding
import com.android.bts.presentation.search.SnippetEntity
import com.bumptech.glide.Glide

class InterestedAdapter(val onClick: (SnippetEntity) -> Unit) : ListAdapter<SnippetEntity, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<SnippetEntity>(){
        override fun areItemsTheSame(oldItem: SnippetEntity, newItem: SnippetEntity): Boolean {
            return oldItem.thumbnail == newItem.thumbnail
        }

        override fun areContentsTheSame(oldItem: SnippetEntity, newItem: SnippetEntity): Boolean {
            return oldItem == newItem
        }
    }
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RecyclerviewInterestedHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InterestedSpotHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val interestedHolder = holder as InterestedSpotHolder
        val currentItem = getItem(position)

//        interestedHolder.thumbnail.setImageURI(currentItem.thumbnail.toUri())
        Glide.with(interestedHolder.thumbnail.context)
            .load(currentItem.thumbnail)
            .centerCrop()
            .into(interestedHolder.thumbnail)

        interestedHolder.traveler.text = currentItem.channelTitle
        interestedHolder.title.text = currentItem.title

    }

    class InterestedSpotHolder(val binding: RecyclerviewInterestedHolderBinding): RecyclerView.ViewHolder(binding.root) {
        var thumbnail = binding.interestedRvHolderIvTitle
        var title = binding.interestedRvHolderTvTitle
        var traveler = binding.interestedRvHolderTvTraveler
    }
}