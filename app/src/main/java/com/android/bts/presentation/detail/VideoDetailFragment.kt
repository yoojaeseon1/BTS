package com.android.bts.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.bts.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class VideoDetailFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_video_detail, container, false)

        // 클릭 리스너 추가
        val textVideoIntro = view.findViewById<TextView>(R.id.text_video_intro)
        val textMemo = view.findViewById<TextView>(R.id.text_memo)

        // 기본 선택 상태 설정
        textVideoIntro.isSelected = true
        textMemo.isSelected = false
        updateBackgrounds(textVideoIntro, textMemo)

        textVideoIntro.setOnClickListener {
            textVideoIntro.isSelected = true
            textMemo.isSelected = false
            updateBackgrounds(textVideoIntro, textMemo)
        }

        textMemo.setOnClickListener {
            textVideoIntro.isSelected = false
            textMemo.isSelected = true
            updateBackgrounds(textVideoIntro, textMemo)
        }

        return view
    }

    // 선택 상태에 따라 배경을 업데이트하는 메서드
    private fun updateBackgrounds(textVideoIntro: TextView, textMemo: TextView) {
        textVideoIntro.setBackgroundResource(
            if (textVideoIntro.isSelected) R.drawable.selected_background else R.drawable.default_background
        )
        textMemo.setBackgroundResource(
            if (textMemo.isSelected) R.drawable.selected_background else R.drawable.default_background
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VideoDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
