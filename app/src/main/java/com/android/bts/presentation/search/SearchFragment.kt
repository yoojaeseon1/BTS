package com.android.bts.presentation.search

import android.animation.ValueAnimator
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.bts.MainViewModel
import com.android.bts.R
import com.android.bts.databinding.FragmentSearchBinding
import com.android.bts.presentation.MainActivity

private const val TAG = "SearchFragment"

class SearchFragment : Fragment() {
    private val _binding: FragmentSearchBinding by lazy {
        FragmentSearchBinding.inflate(layoutInflater)
    }
    val binding get() = _binding

    private lateinit var searchRecyclerViewAdapter: SearchRecyclerViewAdapter
    private val searchViewModel by viewModels<SearchViewModel> {
        SearchViewModelFactory()
    }
    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //리사이클러뷰 어댑터 초기화
        initAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //검색어 변화 감지
        searchViewModel.searchWordLiveData.observe(viewLifecycleOwner) {
            binding.searchEt.setText(searchViewModel.searchWordLiveData.value)
            searchViewModel.getSearchVideoResponse()
        }

        //검색어 입력 후 엔터키
        binding.searchEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.searchEt.text.toString().indexOf("\n") != -1) {
                    searchWithWord()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        //검색어 입력 후 검색버튼
        binding.searchBtn.setOnClickListener {
            searchWithWord()
        }

        //무한 스크롤
        scrollEndListener()

        //추천버튼
        binding.searchBtnRecommend1.setOnClickListener {
            binding.searchRecommendContainer.isVisible = true
            recommendPlace()
        }
        //추천버튼 애니메이션
        initAnimationRecommendBtn()

        //예외처리
        searchViewModel.exceptionLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "검색결과를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    //어댑터 초기화 함수 : 검색결과를 리사이클러뷰로 보여주는 함수. 컨텐츠 클릭시 Detail로 이동.
    private fun initAdapter() {
        searchRecyclerViewAdapter = SearchRecyclerViewAdapter(
            itemClickListener = { item ->
                (activity as MainActivity).replaceDetailFragment(item)}
            ,itemLongClickListener = { item ->  })
        binding.searchRv.adapter = searchRecyclerViewAdapter
        binding.searchRv.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    //검색 함수 : 검색어 변화를 감지해 검색하고 검색결과 변화를 감지해 리사이클러뷰에 반영
    private fun searchWithWord() {
        if (binding.searchEt.text.toString().isBlank()) {
            Toast.makeText(requireContext(), "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show()
        } else {
            searchViewModel.updateSearchWord(binding.searchEt.text.toString())
            binding.searchTvCenter.isVisible = false
            hideKeyboard()

            //검색결과 변화 감지 및 리사이클러뷰 반영
            searchViewModel.searchVideoListLiveData.observe(viewLifecycleOwner) {
                try {
                    searchRecyclerViewAdapter.submitList(
                        searchViewModel.searchVideoListLiveData.value
                    )
                } catch (_: Exception) {
                    binding.searchTvCenter.text = "검색결과를 찾을 수 없습니다."
                }
            }
        }
    }

    //추천버튼 텍스트 컬러 애니메이션 함수
    private fun initAnimationRecommendBtn() {
        val colorAnimation = ValueAnimator.ofArgb(Color.WHITE, Color.GRAY).apply {
            duration = 800
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            addUpdateListener {
                binding.apply {
                    searchBtnRecommend1.setTextColor(it.animatedValue as Int)
                    searchBtnRecommend2.setTextColor(it.animatedValue as Int)
                    searchBtnRecommend3.setTextColor(it.animatedValue as Int)
                    searchBtnRecommend4.setTextColor(it.animatedValue as Int)
                }
            }
        }
        colorAnimation.start()
    }

    //추천 프래그먼트 호출 함수 : 추천버튼 클릭시 프래그먼트로 이동
    private fun recommendPlace() {
        childFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.search_recommend_enter, R.anim.search_recommend_exit)
            .add(R.id.search_recommend_container, SearchRecommendFragment())
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .commit()
    }

    //무한스크롤 함수
    private fun scrollEndListener() {
        binding.searchRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                    if (!binding.searchRv.canScrollVertically(1)) {
                        searchViewModel.getNextSearchVideoResponse()

                }
            }
        })
    }


    //키보드 숨기는 함수
    private fun hideKeyboard() {
        val manager = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(binding.searchEt.windowToken, 0)
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }

}


