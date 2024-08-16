package com.android.bts.presentation.home

import com.android.bts.presentation.search.ItemsEntity

interface InterestedClickLIstener {

    fun onClickLike(itemsEntity: ItemsEntity, holder: InterestedAdapter.InterestedSpotHolder)
    fun onClickDetail(itemsEntity: ItemsEntity, holder: InterestedAdapter.InterestedSpotHolder)

}