package com.android.bts.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
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
}