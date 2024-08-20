package com.android.bts.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.android.bts.MainViewModel
import com.android.bts.R
import com.android.bts.presentation.home.HomeFragment
import com.android.bts.presentation.home.HomeViewModel
import com.android.bts.presentation.search.ItemsEntity
import com.example.app.IntroduceVideoFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class VideoDetailFragment : Fragment() {

    private var videoId: String? = null
    private var videoTitle: String? = null
    private var itemsEntity: ItemsEntity? = null
//    private val homeViewModel by viewModels<HomeViewModel> {
//        HomeViewModelFactory()
//    }

    private val homeViewModel: HomeViewModel by activityViewModels()

    private lateinit var homeFragment: HomeFragment
    private val sharedViewModel : MainViewModel by activityViewModels()

    companion object {
        const val VIDEO_ID_KEY = "videoId"
        const val VIDEO_TITLE_KEY = "videoTitle"
        const val VIDEO_ITEMS_KEY = "snippet"

        // newInstance 메서드로 프래그먼트 생성
        fun newInstance(videoId: String, videoTitle: String): VideoDetailFragment {
            val fragment = VideoDetailFragment()
            val args = Bundle()
            args.putString(VIDEO_ID_KEY, videoId)
            args.putString(VIDEO_TITLE_KEY, videoTitle)
            fragment.arguments = args
            return fragment
        }

        fun newInstance(itemsEntity: ItemsEntity): VideoDetailFragment {
            val fragment = VideoDetailFragment()
            val args = Bundle()
            args.putParcelable(VIDEO_ITEMS_KEY, itemsEntity)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragments = requireActivity().supportFragmentManager.fragments

        for (fragment in fragments) {
            if (fragment is HomeFragment)
                homeFragment = fragment
        }

//        arguments?.let{
//            videoId = it.getString(VIDEO_ID_KEY)
//            videoTitle = it.getString(VIDEO_TITLE_KEY)
//        }

        arguments?.let{
            itemsEntity = it.getParcelable(VIDEO_ITEMS_KEY, ItemsEntity::class.java)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_video_detail, container, false)



//        val videoId = arguments?.getString(VIDEO_ID_KEY)
//        val videoTitle = arguments?.getString(VIDEO_TITLE_KEY)

        val videoTitleTextView = view.findViewById<TextView>(R.id.video_title)
//        videoTitleTextView.text = videoTitle
        videoTitleTextView.text = itemsEntity?.snippet?.title

        val bottomTitleTextView = view.findViewById<TextView>(R.id.bottom_titel_texct_view)
//        bottomTitleTextView.text = videoTitle
        bottomTitleTextView.text = itemsEntity?.snippet?.title

        val youTubePlayerView = view.findViewById<YouTubePlayerView>(R.id.player_view)
//        setupYouTubePlayer(youTubePlayerView, videoId)
        setupYouTubePlayer(youTubePlayerView, itemsEntity?.id?.videoId)

        val textVideoIntro = view.findViewById<TextView>(R.id.text_video_intro)
        val textMemo = view.findViewById<TextView>(R.id.text_memo)



        sharedViewModel.videoPlayLiveData.observe(viewLifecycleOwner) {
            videoTitleTextView.text = it.snippet.title
//            bottomTitleTextView.text = videoTitle
            bottomTitleTextView.text = itemsEntity?.snippet?.title
            setupYouTubePlayer(youTubePlayerView, it.id.videoId)
        }


        // 기본 선택 상태 설정 및 초기 프래그먼트 표시
        textVideoIntro.isSelected = true
        textMemo.isSelected = false
        updateBackgrounds(textVideoIntro, textMemo)
        showFragment(IntroduceVideoFragment())

        textVideoIntro.setOnClickListener {
            textVideoIntro.isSelected = true
            textMemo.isSelected = false
            updateBackgrounds(textVideoIntro, textMemo)
            showFragment(IntroduceVideoFragment())
        }

        textMemo.setOnClickListener {
            textVideoIntro.isSelected = false
            textMemo.isSelected = true
            updateBackgrounds(textVideoIntro, textMemo)
            showFragment(MemoFragment())
        }

//        val backButton = view.findViewById<ImageView>(R.id.back_button)
//        backButton.setOnClickListener {

//            val isCheckedLike = BTSUtils.isCheckedLike(requireActivity(), videoId?:"")
//            Log.d("VideoDetailFragment", "isCheckedLike = ${isCheckedLike}")
//
//            homeFragment.homeViewModel.updateLike(videoId?:"", isCheckedLike)
//            homeFragment.submitAllVideos()


//            parentFragmentManager.popBackStack()
//        }


        return view
    }






    private fun setupYouTubePlayer(youTubePlayerView: YouTubePlayerView, videoId: String?) {
        lifecycle.addObserver(youTubePlayerView) // 생명주기 관리
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                videoId?.let {
                    youTubePlayer.loadVideo(it, 0f) // 영상 로드 및 재생
                }
            }
        })
    }

    private fun updateBackgrounds(textVideoIntro: TextView, textMemo: TextView) {
        textVideoIntro.setBackgroundResource(
            if (textVideoIntro.isSelected) R.drawable.selected_background else R.drawable.default_background
        )
        textMemo.setBackgroundResource(
            if (textMemo.isSelected) R.drawable.selected_background else R.drawable.default_background
        )
    }

    private fun showFragment(fragment: Fragment) {
//        fragment.arguments = Bundle().apply {
//            putString(VIDEO_ID_KEY, arguments?.getString(VIDEO_ID_KEY))
//            putString(VIDEO_TITLE_KEY, arguments?.getString(VIDEO_TITLE_KEY))
//        }
        fragment.arguments = Bundle().apply {
            putParcelable(VIDEO_ITEMS_KEY, itemsEntity)
        }


        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
