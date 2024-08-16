package com.android.bts.presentation.home

import com.android.bts.presentation.search.ItemsEntity

interface HotClickListener {

    fun onClickLike(itemsEntity: ItemsEntity, holder: FavoriteAdapter.HotSpotHolder)
    fun onClickDetail(itemsEntity: ItemsEntity, holder: FavoriteAdapter.HotSpotHolder)
}