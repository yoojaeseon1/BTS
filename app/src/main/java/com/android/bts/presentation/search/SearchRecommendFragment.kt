package com.android.bts.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.android.bts.R
import com.android.bts.databinding.FragmentSearchRecommendBinding

class SearchRecommendFragment : Fragment(R.layout.fragment_search_recommend) {
    private var _binding: FragmentSearchRecommendBinding? = null
    private val binding get() = _binding as FragmentSearchRecommendBinding
    private val sharedViewModel : MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchRecommendBinding.inflate(inflater, container, false)



        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)









//            Glide.with(requireContext())
//                .load(sharedViewModel.videoPlayLiveData.value?.snippet?.thumbnail)
//                .into(binding.videoPlayPlayerVv)


//        binding.videoPlay.setTransitionListener(object : MotionLayout.TransitionListener {
//            override fun onTransitionStarted(
//                motionLayout: MotionLayout?,
//                startId: Int,
//                endId: Int
//            ) {
//            }

//            override fun onTransitionChange(
//                motionLayout: MotionLayout?,
//                startId: Int,
//                endId: Int,
//                progress: Float
//            ) {
//                binding.let { mbinding ->
//                    (activity as MainActivity).also { mainActivity ->
//                        mainActivity.findViewById<MotionLayout>(R.id.video_play).progress =
//                            abs(progress)
//                    }
//                }
//            }
//
//            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
//            }
//
//            override fun onTransitionTrigger(
//                motionLayout: MotionLayout?,
//                triggerId: Int,
//                positive: Boolean,
//                progress: Float
//            ) {
//            }
//
//    })
}

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}









