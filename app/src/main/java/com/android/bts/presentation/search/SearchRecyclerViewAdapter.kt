package com.android.bts.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.bts.databinding.RecyclerviewSearchHolderBinding
import com.bumptech.glide.Glide

class SearchRecyclerViewAdapter(
    private val itemClickListener: (item: ItemsEntity) -> Unit
) : ListAdapter<ItemsEntity, SearchRecyclerViewAdapter.Holder>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ItemsEntity>() {
            override fun areItemsTheSame(oldItem: ItemsEntity, newItem: ItemsEntity): Boolean {
                return oldItem.id.videoId == newItem.id.videoId
            }

            override fun areContentsTheSame(oldItem: ItemsEntity, newItem: ItemsEntity): Boolean {
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
        private val itemClickListener: (ItemsEntity) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: ItemsEntity) {
            binding.apply {
                searchRvHolderTvTitle.text = item.snippet.title
                searchRvHolderTvTraveler.text = item.snippet.channelTitle
                homeHolder.setOnClickListener {
                    itemClickListener(item)
                }
            }
            Glide.with(itemView.context)
                .load(item.snippet.thumbnail)
                .into(binding.searchRvHolderIvTraveler)

            Glide.with(itemView.context)
                .load(item.snippet.thumbnail)
                .into(binding.searchRvHolderIvTitle)
        }
    }
}




