package com.android.bts.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.bts.data.VideoEntity
import com.android.bts.databinding.RecyclerviewSearchHolderBinding
import com.bumptech.glide.Glide

class SearchRecyclerViewAdapter(
    private val itemClickListener: (item: VideoEntity) -> Unit
) : ListAdapter<VideoEntity, SearchRecyclerViewAdapter.Holder>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<VideoEntity>() {
            override fun areItemsTheSame(oldItem: VideoEntity, newItem: VideoEntity): Boolean {
                return oldItem.uId == newItem.uId
            }

            override fun areContentsTheSame(oldItem: VideoEntity, newItem: VideoEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            RecyclerviewSearchHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return Holder(binding, itemClickListener)
    }


    override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.bind(getItem(position))
    }

    class Holder(
        private val binding: RecyclerviewSearchHolderBinding,
        private val itemClickListener: (VideoEntity) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: VideoEntity) {
            binding.apply {
                searchRvHolderTvTitle.text = item.title
                searchRvHolderTvTraveler.text = item.travelerName
                homeHolder.setOnClickListener {
                    itemClickListener(item)
                }
            }
            Glide.with(itemView.context)
                .load(item.travelerIcon)
                .into(binding.searchRvHolderIvTraveler)

//            Glide.with(itemView.context)
//                .load(item.thumbNail)
//                .into(binding.searchRvHolderIvTitle)
        }
    }
}




