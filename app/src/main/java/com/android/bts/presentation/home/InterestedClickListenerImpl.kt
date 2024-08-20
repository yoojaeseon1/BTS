package com.android.bts.presentation.home

import android.app.Activity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import com.android.bts.BTSUtils
import com.android.bts.R
import com.android.bts.presentation.detail.VideoDetailFragment
import com.android.bts.presentation.save.LikedVideo
import com.android.bts.presentation.search.ItemsEntity
import com.example.app.save.SavedVideoViewModel

class InterestedClickListenerImpl(val fragment: Fragment) : InterestedClickLIstener{

    private val savedVideoViewModel: SavedVideoViewModel by fragment.activityViewModels()

    override fun onClickLike(itemsEntity: ItemsEntity,
                             holder: InterestedAdapter.InterestedSpotHolder
    ) {

        val likedVideo = LikedVideo(
            itemsEntity.id.videoId,
            itemsEntity.snippet.title,
            itemsEntity.snippet.channelTitle,
            itemsEntity.snippet.thumbnail
        )
        if(itemsEntity.snippet.isLike) {
            BTSUtils.deleteLike(fragment.requireActivity(), itemsEntity.id.videoId)
            itemsEntity.snippet.isLike = false
            holder.like.isVisible = false
            savedVideoViewModel.deleteLike(likedVideo)
        } else {
//            BTSUtils.addLike(context, itemsEntity.id.videoId)
            BTSUtils.addLike(fragment.requireActivity(), itemsEntity)
//                holder.like.setImageResource(R.drawable.icon_like_full)
            itemsEntity.snippet.isLike = true
            holder.like.isVisible = true
            savedVideoViewModel.likeVideo(likedVideo)
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
//        (fragment as FragmentActivity).supportFragmentManager.beginTransaction()
        fragment.requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame, videoDetailFragment)
            .addToBackStack(null)
            .commit()
    }
}