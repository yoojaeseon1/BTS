package com.android.bts.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.bts.R
import com.android.bts.databinding.ActivityMainBinding

import com.android.bts.presentation.search.MainViewModel

import com.android.bts.presentation.my.MyVideoFragment
import com.android.bts.presentation.search.VideoPlayFragment
import com.google.android.material.tabs.TabLayout

import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var searchSharedViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //액티비티 공유 뷰모델 갖다 쓰세요~!
        //private val sharedViewModel : MainViewModel by activityViewModels()


        //레이아웃 초기화
        initLayout()
        searchSharedViewModel = ViewModelProvider(this)[MainViewModel::class.java]




    }

    //레이아웃 초기화 함수 : 뷰페이저, 탭레이아웃 연결
    private fun initLayout() {
        val viewPager = binding.pager
        val mainViewPagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = mainViewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "홈"
                    tab.setIcon(R.drawable.ic_home)
                }

                1 -> {
                    tab.text = "검색"
                    tab.setIcon(R.drawable.ic_search)
                }

                2 -> {
                    tab.text = "저장"
                    tab.setIcon(R.drawable.ic_down)
                }

                3 -> {
                    tab.text = "구독"
                    tab.setIcon(R.drawable.ic_like)
                }
            }
        }.attach()
    }

    fun showVideo() {
            supportFragmentManager.beginTransaction()
            .replace(R.id.search_play_container, VideoPlayFragment())
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .commit()
    }


    }



//    private val binding: ActivityMainBinding by lazy {
//        DataBindingUtil.setContentView(this, R.layout.activity_main)
//    }
//
//    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
//        override fun onPageSelected(position: Int) {
//            super.onPageSelected(position)
//            when(position) {
//                0 -> {
//                    val homeFragment = HomeFragment()
//                    supportFragmentManager
//                        .beginTransaction()
//                        .replace(R.id.frame_layout, homeFragment)
//                        .addToBackStack(null)
//                        .commit()
//                }
//                1 -> {
//                    val searchFragment = SearchFragment()
//                    supportFragmentManager
//                        .beginTransaction()
//                        .replace(R.id.frame_layout, searchFragment)
//                        .addToBackStack(null)
//                        .commit()
//                }
//                2 -> {
//                    val videoDetailFragment = VideoDetailFragment()
//                    supportFragmentManager
//                        .beginTransaction()
//                        .replace(R.id.frame_layout, videoDetailFragment)
//                        .addToBackStack(null)
//                        .commit()
//                }
//
//                3 -> {
//                    val myVideoFragment = MyVideoFragment()
//                    supportFragmentManager
//                        .beginTransaction()
//                        .replace(R.id.frame_layout, myVideoFragment)
//                        .addToBackStack(null)
//                        .commit()
//                }
//                else -> {
//                    throw IllegalStateException("invalid position ${position}")
//                }
//            }
//        }
//    }
//
//    private lateinit var viewPager: ViewPager2
//    private val tabTitles = arrayOf("홈", "비디오검색", "상세정보", "마이페이지")
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//
//        with(binding) {
//            tabLayout.addOnTabSelectedListener(this@MainActivity)
//            viewPager= pager
//            viewPager.adapter = ViewPagerAdapter(this@MainActivity)
//            viewPager.registerOnPageChangeCallback(pageChangeCallback)
//            viewPager.offscreenPageLimit = 4
//            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//                tab.text = tabTitles[position]
//            }.attach()
//        }
//
//
//    }
//
//    override fun onTabSelected(tab: TabLayout.Tab?) {
//        if(tab != null) {
//            when(tab.position) {
//                0 -> viewPager.setCurrentItem(0, false)
//                1 -> viewPager.setCurrentItem(1, false)
//                2 -> viewPager.setCurrentItem(2, false)
//                3 -> viewPager.setCurrentItem(3, false)
//            }
//        }
//    }
//
//    override fun onTabUnselected(tab: TabLayout.Tab?) {
//        if(tab != null) {
//            when(tab.position) {
//                0 -> viewPager.setCurrentItem(0, false)
//                1 -> viewPager.setCurrentItem(1, false)
//                2 -> viewPager.setCurrentItem(2, false)
//                3 -> viewPager.setCurrentItem(3, false)
//            }
//        }
//    }
//
//    override fun onTabReselected(tab: TabLayout.Tab?) { }
//
//
//
//    // fragment 화면 겹치는 현상 때 사용
//
//    fun showViewPager() = with(binding) {
//        frameLayout.isVisible = false
//        pager.isVisible = true
//    }
//
//    fun hideViewPager() = with(binding) {
//        frameLayout.isVisible = true
//        pager.isVisible = false
//    }
//}
