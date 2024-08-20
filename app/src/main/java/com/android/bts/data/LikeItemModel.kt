package com.android.bts.data

data class LikeItemModel(
    val id: String,
    val title: String,
    val channelTitle: String,
    val thumbnail: String,
    var isLike: Boolean = false
)
