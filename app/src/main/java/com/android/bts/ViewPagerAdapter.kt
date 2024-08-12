package com.android.bts

import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.bts.presentation.detail.VideoDetailFragment
import com.android.bts.presentation.home.HomeFragment
import com.android.bts.presentation.my.MyVideoFragment
import com.android.bts.presentation.search.SearchFragment

class ViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount() = 4

    override fun createFragment(position: Int) = when(position) {
        0 -> HomeFragment()
        1 -> SearchFragment()
        2 -> VideoDetailFragment()
        3 -> MyVideoFragment()
        else -> throw IllegalStateException("invalid position ${position}")
    }
}