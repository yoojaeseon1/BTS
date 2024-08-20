package com.android.bts.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.bts.databinding.RecyclerviewInterestedHolderBinding
import com.android.bts.presentation.search.ItemsEntity
import com.bumptech.glide.Glide

class InterestedAdapter(private val videoClick: InterestedClickLIstener) : ListAdapter<ItemsEntity, RecyclerView.ViewHolder>
    (object : DiffUtil.ItemCallback<ItemsEntity>(){
        override fun areItemsTheSame(oldItem:ItemsEntity, newItem:ItemsEntity): Boolean {
//            return oldItem.snippet.thumbnail == newItem.snippet.thumbnail
            return oldItem.id.videoId == newItem.id.videoId
                    && oldItem.snippet.isLike == newItem.snippet.isLike
        }

        override fun areContentsTheSame(oldItem:ItemsEntity, newItem: ItemsEntity): Boolean {
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

        holder.binding.root.setOnLongClickListener {
            videoClick.onClickLike(currentItem, holder)
            true
        }

        holder.binding.root.setOnClickListener {
            videoClick.onClickDetail(currentItem, holder)
        }

//        interestedHolder.thumbnail.setImageURI(currentItem.thumbnail.toUri())
        Glide.with(interestedHolder.thumbnail.context)
            .load(currentItem.snippet.thumbnail)
            .centerCrop()
            .into(interestedHolder.thumbnail)

        if(currentItem.snippet.isLike) {
            interestedHolder.like.visibility = ImageView.VISIBLE
        } else {
            interestedHolder.like.visibility = ImageView.GONE
        }

        interestedHolder.traveler.text = currentItem.snippet.channelTitle
        interestedHolder.title.text = currentItem.snippet.title

    }

    class InterestedSpotHolder(val binding: RecyclerviewInterestedHolderBinding): RecyclerView.ViewHolder(binding.root) {
        var thumbnail = binding.interestedRvHolderIvTitle
        var title = binding.interestedRvHolderTvTitle
        var traveler = binding.interestedRvHolderTvTraveler
        val like = binding.ivLike
    }
}