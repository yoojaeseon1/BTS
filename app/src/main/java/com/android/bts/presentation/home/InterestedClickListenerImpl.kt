package com.android.bts.presentation.home

import android.app.Activity
import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.android.bts.BTSUtils
import com.android.bts.R
import com.android.bts.presentation.detail.VideoDetailFragment
import com.android.bts.presentation.search.ItemsEntity

class InterestedClickListenerImpl(val context: Activity) : InterestedClickLIstener{

    override fun onClickLike(itemsEntity: ItemsEntity,
                             holder: InterestedAdapter.InterestedSpotHolder
    ) {
        if(itemsEntity.snippet.isLike) {
            BTSUtils.deleteLike(context, itemsEntity.id.videoId)
            itemsEntity.snippet.isLike = false
            holder.like.isVisible = false
        } else {
            BTSUtils.addLike(context, itemsEntity.id.videoId)
//                holder.like.setImageResource(R.drawable.icon_like_full)
            itemsEntity.snippet.isLike = true
            holder.like.isVisible = true
        }
    }

    override fun onClickDetail(itemsEntity: ItemsEntity,
        holder: InterestedAdapter.InterestedSpotHolder
    ) {
        // VideoDetailFragment로 전환
//        val videoDetailFragment = VideoDetailFragment.newInstance(
//            itemsEntity.id.videoId,
//            itemsEntity.snippet.title
//        )

        val videoDetailFragment = VideoDetailFragment.newInstance(
            itemsEntity
        )
//            (context as FragmentActivity).supportFragmentManager.beginTransaction()
        (context as FragmentActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame, videoDetailFragment)
            .addToBackStack(null)
            .commit()
    }
}