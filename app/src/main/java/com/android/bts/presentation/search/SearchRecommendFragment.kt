package com.android.bts.presentation.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.android.bts.R
import com.android.bts.data.remote.RecommendList
import com.android.bts.databinding.FragmentSearchRecommendBinding
import com.bumptech.glide.Glide

class SearchRecommendFragment : Fragment(R.layout.fragment_search_recommend) {
    private var _binding: FragmentSearchRecommendBinding? = null
    private val binding get() = _binding as FragmentSearchRecommendBinding
//    private val sharedViewModel : SearchViewModel by viewModels<SearchViewModel>({requireParentFragment()})
    private val sharedViewModel by viewModels<SearchViewModel> {
        SearchViewModelFactory()
    }


    private var animationSwit = mutableMapOf("view" to "first", "button" to "left")
    private val recommendPlaceList = RecommendList().getRecommendList()


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


        //
        sharedViewModel.recommendNumberData.observe(viewLifecycleOwner) {
            when (it) {
                recommendPlaceList.size -> sharedViewModel.setRecommendNumber(recommendPlaceList.size)
                -1 -> sharedViewModel.setRecommendNumber(recommendPlaceList.size)
                else -> recommendPlace(it)
            }
        }


        //닫기버튼
        binding.searchRecommendBtnExit.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .remove(this).commit()
        }
        //왼쪽 버튼
        binding.searchRecommendBtnLeft.setOnClickListener {
            Log.d("클릭", "${animationSwit["view"]}")
            animationSwit["button"] = "left"
            sharedViewModel.nextRecommendNumber()

        }
        //오른쪽 버튼
        binding.searchRecommendBtnRight.setOnClickListener {
            animationSwit["button"] = "right"
            sharedViewModel.previousRecommendNumber()

        }
    }


    private fun recommendPlace(indexNumber: Int) {

        val imageView = arrayOf(binding.searchRecommendIvFirst, binding.searchRecommendIvSecond)
        val animation = arrayOf(
            AnimationUtils.loadAnimation(requireContext(), R.anim.search_recommend_left_first),
            AnimationUtils.loadAnimation(requireContext(), R.anim.search_recommend_left_second),
            AnimationUtils.loadAnimation(requireContext(), R.anim.search_recommend_right_first),
            AnimationUtils.loadAnimation(requireContext(), R.anim.search_recommend_right_second)
        )
        val recommendPlace = recommendPlaceList[indexNumber]

        binding.apply {
            searchRecommendTvSubTitle.text = recommendPlace.subTitle
            searchRecommendTvTitle.text = recommendPlace.title
            searchRecommendTvInfo.text = recommendPlace.info
        }
        when {
            animationSwit["button"] == "left" -> {
                when {
                    animationSwit["view"] == "first" -> {
                        imageView[0].startAnimation(animation[0])
                        imageView[1].startAnimation(animation[1])
                        Glide.with(requireContext())
                            .load(recommendPlace.thumbnail.toUri())
                            .timeout(6000)
                            .into(imageView[1])
                        animationSwit["view"] = "second"
                    }
                    animationSwit["view"] == "second" -> {
                        imageView[0].startAnimation(animation[1])
                        imageView[1].startAnimation(animation[0])
                        Glide.with(requireContext())
                            .load(recommendPlace.thumbnail.toUri())
                            .timeout(6000)
                            .into(imageView[0])
                        animationSwit["view"] = "first"
                    }
                }
            }

            animationSwit["button"] == "right" -> {
                when {
                    animationSwit["view"] == "first" -> {
                        imageView[0].startAnimation(animation[2])
                        imageView[1].startAnimation(animation[3])
                        Glide.with(requireContext())
                            .load(recommendPlace.thumbnail.toUri())
                            .timeout(6000)
                            .into(imageView[1])
                        animationSwit["view"] = "second"
                    }
                    animationSwit["view"] == "second" -> {
                        imageView[0].startAnimation(animation[3])
                        imageView[1].startAnimation(animation[2])
                        Glide.with(requireContext())
                            .load(recommendPlace.thumbnail.toUri())
                            .timeout(6000)
                            .into(imageView[0])
                        animationSwit["view"] = "first"
                    }
                }



            }
        }

        //마음에 들어요 버튼
        binding.searchRecommendBtnAccept.setOnClickListener {
            sharedViewModel.updateSearchWord(recommendPlace.searchWord)
            sharedViewModel.getSearchVideoResponse()
            parentFragmentManager.beginTransaction().remove(this).commit()
        }

    }






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


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}









