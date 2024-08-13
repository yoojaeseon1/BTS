//package com.android.bts.presentation.search
//
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//
//class SearchRecyclerViewAdapter(
//        private val itemClickListener: (item: ContentModel) -> Unit
//    ) : ListAdapter<ContentModel, RecyclerView.ViewHolder>(diffUtil) {
//
//        companion object {
//            val diffUtil = object : DiffUtil.ItemCallback<ContentModel>() {
//                override fun areItemsTheSame(oldItem: ContentModel, newItem: ContentModel): Boolean {
//                    return oldItem.uId == newItem.uId
//                }
//                override fun areContentsTheSame(oldItem: ContentModel, newItem: ContentModel): Boolean {
//                    return oldItem == newItem
//                }
//            }
//        }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//                val binding =
//                    RecyclerviewHomeHolderBinding.inflate(
//                        LayoutInflater.from(parent.context),
//                        parent,
//                        false)
//                ItemHolder(binding, itemClickListener)
//            }
//
//
//        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//            if(holder is ItemHolder)
//                holder.bind(getItem(position))
//        }
//
//        class ItemHolder(
//            private val binding: RecyclerviewHomeHolderBinding,
//            private val itemClickListener: (ContentModel) -> Unit
//        ) :
//            ViewHolder(binding.root) {
//
//
//            fun bind(item: ContentModel) {
//                binding.apply {
//                    homeHolderTvTitle.text = item.title
//                    homeHolderTvDateTime.text = Util.makeDateTimeFormat(item.dateTime)
//                    binding.homeHolderIvSelected.isVisible = item.selectedContent
//                    binding.homeHolderIvVideo.isVisible = item.type != "image"
//                    homeHolder.setOnClickListener {
//                        itemClickListener(item)
//                    }
//                }
//                Glide.with(itemView.context)
//                    .load(item.thumbnail)
//                    .into(binding.homeHolderIvTitle)
//            }
//        }
//    }
//
//
//
//
//}