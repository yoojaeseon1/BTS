package com.android.bts.presentation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.bts.presentation.detail.VideoDetailFragment
import com.android.bts.presentation.home.HomeFragment
import com.android.bts.presentation.my.MyVideoFragment
import com.android.bts.presentation.search.SearchFragment
import com.example.app.save.SavedFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private val fragments = listOf(HomeFragment(), SearchFragment(), SavedFragment(), MyVideoFragment())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }



//    override fun getItemCount() = 4
//
//    override fun createFragment(position: Int) = when(position) {
//        0 -> HomeFragment()
//        1 -> SearchFragment()
//        2 -> VideoDetailFragment()
//        3 -> MyVideoFragment()
//        else -> throw IllegalStateException("invalid position ${position}")
//    }
}