package com.android.bts.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.bts.R
import com.example.app.IntroduceVideoFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class VideoDetailFragment : Fragment() {

    companion object {
        private const val VIDEO_ID_KEY = "videoId"

        // newInstance 메서드로 프래그먼트 생성
        fun newInstance(videoId: String): VideoDetailFragment {
            val fragment = VideoDetailFragment()
            val args = Bundle()
            args.putString(VIDEO_ID_KEY, videoId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_video_detail, container, false)

        val videoId = arguments?.getString(VIDEO_ID_KEY)
        val youTubePlayerView = view.findViewById<YouTubePlayerView>(R.id.player_view)

        setupYouTubePlayer(youTubePlayerView, videoId)

        val textVideoIntro = view.findViewById<TextView>(R.id.text_video_intro)
        val textMemo = view.findViewById<TextView>(R.id.text_memo)

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
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
