package com.android.bts.presentation.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.android.bts.MainViewModel
import com.android.bts.R
import com.android.bts.presentation.MainActivity
import com.android.bts.presentation.home.HomeFragment
import com.android.bts.presentation.search.ItemsEntity
import com.example.app.IntroduceVideoFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class VideoDetailFragment : Fragment() {

    private var itemsEntity: ItemsEntity? = null
    private lateinit var homeFragment: HomeFragment
    private val sharedViewModel : MainViewModel by activityViewModels()

    companion object {
        const val VIDEO_ID_KEY = "videoId"
        const val VIDEO_TITLE_KEY = "videoTitle"
        const val VIDEO_ITEMS_KEY = "snippet"

        // newInstance 메서드로 프래그먼트 생성
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

        arguments?.let{
            itemsEntity = it.getParcelable(VIDEO_ITEMS_KEY, ItemsEntity::class.java)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_video_detail, container, false)

        val videoTitleTextView = view.findViewById<TextView>(R.id.video_title)
        videoTitleTextView.text = itemsEntity?.snippet?.title

        val bottomTitleTextView = view.findViewById<TextView>(R.id.bottom_titel_texct_view)
        bottomTitleTextView.text = itemsEntity?.snippet?.title

        val youTubePlayerView = view.findViewById<YouTubePlayerView>(R.id.player_view)
        setupYouTubePlayer(youTubePlayerView, itemsEntity?.id?.videoId)

        val textVideoIntro = view.findViewById<TextView>(R.id.text_video_intro)
        val textMemo = view.findViewById<TextView>(R.id.text_memo)



        sharedViewModel.videoPlayLiveData.observe(viewLifecycleOwner) {
            videoTitleTextView.text = it.snippet.title
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

        closeBtnListener(view)
        return view
    }

private fun closeBtnListener(view: View) {
    val closeBtn = view.findViewById<ImageView>(R.id.detail_btn_exit)
    closeBtn?.setOnClickListener {
        (activity as MainActivity).supportFragmentManager.popBackStack()
    }
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
        fragment.arguments = Bundle().apply {
            putParcelable(VIDEO_ITEMS_KEY, itemsEntity)
        }


        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
