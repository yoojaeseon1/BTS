package com.android.bts.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.bts.databinding.RecyclerviewSearchHolderBinding
import com.bumptech.glide.Glide

class SearchRecyclerViewAdapter(
    private val itemClickListener: (item: SnippetEntity) -> Unit
) : ListAdapter<SnippetEntity, SearchRecyclerViewAdapter.Holder>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<SnippetEntity>() {
            override fun areItemsTheSame(oldItem: SnippetEntity, newItem: SnippetEntity): Boolean {
                return oldItem.uId == newItem.uId
            }

            override fun areContentsTheSame(oldItem: SnippetEntity, newItem: SnippetEntity): Boolean {
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
        private val itemClickListener: (SnippetEntity) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: SnippetEntity) {
            binding.apply {
                searchRvHolderTvTitle.text = item.title
                searchRvHolderTvTraveler.text = item.channelTitle
                homeHolder.setOnClickListener {
                    itemClickListener(item)
                }
            }
            Glide.with(itemView.context)
                .load(item.thumbnails)
                .into(binding.searchRvHolderIvTraveler)

            Glide.with(itemView.context)
                .load(item.thumbnails)
                .into(binding.searchRvHolderIvTitle)
        }
    }
}




