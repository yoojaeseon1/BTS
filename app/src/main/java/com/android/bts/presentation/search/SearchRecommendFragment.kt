package com.android.bts.presentation.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.bts.R
import com.android.bts.data.remote.RecommendList
import com.android.bts.databinding.FragmentSearchRecommendBinding
import com.bumptech.glide.Glide


private const val TAG = "SearchRecommendFragment"

class SearchRecommendFragment : Fragment(R.layout.fragment_search_recommend) {
    private var _binding: FragmentSearchRecommendBinding? = null
    private val binding get() = _binding as FragmentSearchRecommendBinding
    private lateinit var sharedViewModel :SearchViewModel
    private var animationSwitch = mutableMapOf("VIEW" to "first", "BUTTON" to "left")
    private val recommendPlaceList = RecommendList().getRecommendList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchRecommendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel = ViewModelProvider(requireParentFragment()).get(SearchViewModel::class.java)

        //추천
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
                .setCustomAnimations(R.anim.search_recommend_exit,R.anim.search_recommend_exit)
                .remove(this).commit()
        }

        //왼쪽 버튼
        binding.searchRecommendBtnLeft.setOnClickListener {
            Log.d("클릭", "${animationSwitch["VIEW"]}")
            animationSwitch["BUTTON"] = "left"
            sharedViewModel.nextRecommendNumber()

        }
        //오른쪽 버튼
        binding.searchRecommendBtnRight.setOnClickListener {
            animationSwitch["BUTTON"] = "right"
            sharedViewModel.previousRecommendNumber()

        }
    }
    //클릭한 버튼에 따라 여행지를 추천해주는 함수
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
            animationSwitch["BUTTON"] == "left" -> {
                when {
                    animationSwitch["VIEW"] == "first" -> {
                        imageView[0].startAnimation(animation[0])
                        imageView[1].startAnimation(animation[1])
                        Glide.with(requireContext())
                            .load(recommendPlace.thumbnail.toUri())
                            .timeout(6000)
                            .into(imageView[1])
                        animationSwitch["VIEW"] = "second"
                    }
                    animationSwitch["VIEW"] == "second" -> {
                        imageView[0].startAnimation(animation[1])
                        imageView[1].startAnimation(animation[0])
                        Glide.with(requireContext())
                            .load(recommendPlace.thumbnail.toUri())
                            .timeout(6000)
                            .into(imageView[0])
                        animationSwitch["VIEW"] = "first"
                    }
                }
            }

            animationSwitch["BUTTON"] == "right" -> {
                when {
                    animationSwitch["VIEW"] == "first" -> {
                        imageView[0].startAnimation(animation[2])
                        imageView[1].startAnimation(animation[3])
                        Glide.with(requireContext())
                            .load(recommendPlace.thumbnail.toUri())
                            .timeout(6000)
                            .into(imageView[1])
                        animationSwitch["VIEW"] = "second"
                    }
                    animationSwitch["VIEW"] == "second" -> {
                        imageView[0].startAnimation(animation[3])
                        imageView[1].startAnimation(animation[2])
                        Glide.with(requireContext())
                            .load(recommendPlace.thumbnail.toUri())
                            .timeout(6000)
                            .into(imageView[0])
                        animationSwitch["VIEW"] = "first"
                    }
                }
            }
        }

        //마음에 들어요 버튼
        binding.searchRecommendBtnAccept.setOnClickListener {
            sharedViewModel.updateSearchWord(recommendPlace.searchWord)
            Log.d(TAG, "써치R프래그먼트 ${sharedViewModel.searchWordLiveData.value}")
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.search_recommend_exit,R.anim.search_recommend_exit)
                .remove(this).commit()
        }
    }


    override fun onResume() {
        super.onResume()

        //애니메이션
        val blinkAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.search_recommend_btn_blink)
        binding.searchRecommendBtnLeft.startAnimation(blinkAnimation)
        binding.searchRecommendBtnRight.startAnimation(blinkAnimation)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}









