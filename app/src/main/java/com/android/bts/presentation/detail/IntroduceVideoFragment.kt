package com.example.app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.bts.BTSUtils
import com.android.bts.R
import com.android.bts.data.remote.CommentRemoteDataSource
import com.android.bts.data.remote.CommentRepository
import com.android.bts.databinding.FragmentIntroduceVideoBinding
import com.android.bts.presentation.detail.CommentAdapter
import com.android.bts.presentation.detail.IntroduceVideoViewModel
import com.android.bts.presentation.detail.IntroduceVideoViewModelFactory
import com.android.bts.presentation.detail.VideoDetailFragment.Companion.VIDEO_ID_KEY
import com.android.bts.presentation.detail.VideoDetailFragment.Companion.VIDEO_TITLE_KEY
import com.android.bts.presentation.home.HomeFragment
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class IntroduceVideoFragment : Fragment() {

    private lateinit var binding: FragmentIntroduceVideoBinding
    private lateinit var commentAdapter: CommentAdapter
    private val viewModel: IntroduceVideoViewModel by viewModels {
        IntroduceVideoViewModelFactory(CommentRepository(apiService))
    }

    private var videoId: String? = null
    private var videoTitle: String? = null

    private val apiService: CommentRemoteDataSource by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/youtube/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CommentRemoteDataSource::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let{
            videoId = it.getString(VIDEO_ID_KEY)?:""
            videoTitle = it.getString(VIDEO_TITLE_KEY)?:""
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIntroduceVideoBinding.inflate(inflater, container, false)

        // RecyclerView 설정
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        commentAdapter = CommentAdapter(emptyList())
        binding.recyclerView.adapter = commentAdapter

        // ViewModel 설정 및 댓글 불러오기
//        val videoId = arguments?.getString("VIDEO_ID_KEY")
//        val videoTitle = arguments?.getString("VIDEO_TITLE_KEY")

        if (videoId == null) {
            Log.e("IntroduceVideoFragment", "Video ID is null")
        } else {
            Log.d("IntroduceVideoFragment", "Video ID: $videoId, Video Title: $videoTitle")
            val apiKey = "AIzaSyBrhEMfHwQcNHcvtVvwQhe0fbILK7JWL14"
            viewModel.fetchComments(videoId?:"", apiKey)
        }

        // 댓글 데이터 관찰하여 업데이트
        viewModel.comments.observe(viewLifecycleOwner) { comments ->
            Log.d("IntroduceVideoFragment", "Number of comments observed: ${comments.size}")
            commentAdapter.updateComments(comments)
        }

        // Save 버튼 클릭 기능
        binding.saveButton.setOnClickListener {
            videoId?.let {
                Log.d("IntroduceVideoFragment", "Saving video: $videoTitle with ID: $videoId")
                // 비디오 정보 저장 (SavedVideoRepository에 저장 로직 추가)
            }
        }

        val isCheckedLike = BTSUtils.isCheckedLike(requireActivity(), videoId ?: "")
        Log.d("IntroduceVideoFragment", "isCheckedLike = ${isCheckedLike}")
        if(isCheckedLike)
            binding.likeIcon.setImageResource(R.drawable.icon_like_full)

        binding.likeIcon.setOnClickListener{
            val isCheckedLike = BTSUtils.isCheckedLike(requireActivity(), videoId ?: "")
            if(isCheckedLike) {
                binding.likeIcon.setImageResource(R.drawable.icon_like_empty)
                BTSUtils.deleteLike(requireActivity(), videoId?:"")
            } else {
                binding.likeIcon.setImageResource(R.drawable.icon_like_full)
                BTSUtils.addLike(requireActivity(), videoId?:"")
            }
        }

        return binding.root
    }
}
