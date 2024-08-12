package com.android.bts

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

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