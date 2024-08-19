package com.android.bts.presentation.home

import android.app.Activity
import androidx.core.view.isVisible
import com.android.bts.BTSUtils
import com.android.bts.presentation.search.ItemsEntity

class HotClickListenerImpl(val context: Activity) : HotClickListener{

    override fun onClickLike(itemsEntity: ItemsEntity, holder: HotSpotAdapter.HotSpotHolder) {
        if(itemsEntity.snippet.isLike) {
            BTSUtils.deleteLike(context, itemsEntity.id.videoId)
            itemsEntity.snippet.isLike = false
            holder.like.isVisible = false
        } else {
            BTSUtils.addLike(context, itemsEntity.id.videoId)
            itemsEntity.snippet.isLike = true
            holder.like.isVisible = true
        }
    }

    override fun onClickDetail(itemsEntity: ItemsEntity, holder: HotSpotAdapter.HotSpotHolder) {

    }
}