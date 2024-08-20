package com.android.bts.presentation.my

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.bts.data.LikeItemModel
import com.android.bts.databinding.RecyclerviewInterestedHolderBinding
import com.bumptech.glide.Glide

class LikeRecyclerViewAdapter(var mContext: Context) :
    RecyclerView.Adapter<LikeRecyclerViewAdapter.LikeViewHolder>() {

    var items = mutableListOf<LikeItemModel>()

    interface onItemClickListener {
        fun onItemClick(data: LikeItemModel, position: Int)
    }

    private var itemClickListener: onItemClickListener? = null

    fun setOnItemClickListener(listener: onItemClickListener) {
        this.itemClickListener = listener
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LikeViewHolder {
        val binding = RecyclerviewInterestedHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LikeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(
        holder: LikeViewHolder,
        position: Int
    ) {

        Glide.with(mContext)
            .load(items[position].thumbnail)
            .centerCrop()
            .into(holder.thumbnail)

        holder.title.text = items[position].title
        holder.traveler.text = items[position].channelTitle

        holder.item.setOnClickListener {
            itemClickListener?.onItemClick(items[position], position)
        }
    }


    inner class LikeViewHolder(binding: RecyclerviewInterestedHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var thumbnail = binding.interestedRvHolderIvTitle
        var title = binding.interestedRvHolderTvTitle
        var traveler = binding.interestedRvHolderTvTraveler
        var like = binding.ivLike
        var item = binding.homeHolder

        init {
            like.visibility = View.GONE
        }
    }
}