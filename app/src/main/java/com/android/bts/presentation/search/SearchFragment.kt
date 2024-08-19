package com.android.bts.presentation.search

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
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
import com.android.bts.R
import com.android.bts.databinding.FragmentSearchBinding
import com.android.bts.presentation.MainActivity


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding as FragmentSearchBinding
    private lateinit var searchRecyclerViewAdapter: SearchRecyclerViewAdapter
    private val searchViewModel by viewModels<SearchViewModel> {
        SearchViewModelFactory()
    }
    private val sharedViewModel : MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        //리사이클러뷰 어댑터 초기화
        initAdapter()

        //검색어 입력 후 검색버튼
        binding.searchBtn.setOnClickListener {
            searchWithWord()
        }

        //무한 스크롤
        scrollEndListener()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        //검색어 입력 후 키보드 엔터키
        binding.searchEt.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                searchWithWord()
                return@setOnKeyListener false
            } else return@setOnKeyListener false
        }

        //추천버튼
        binding.searchBtnRecommend1.setOnClickListener {
            binding.searchRecommendContainer.isVisible = true
            recommendPlace()
        }



    }


    //어댑터 초기화 함수 : 검색결과를 리사이클러뷰로 보여주는 함수. 컨텐츠 클릭시 Detail로 이동.
    private fun initAdapter() {
        searchRecyclerViewAdapter = SearchRecyclerViewAdapter(
            itemClickListener = { item ->
                Log.d("써치", "${sharedViewModel.videoPlayLiveData.value}")
                sharedViewModel.updateVideoPlayer(item)
                (activity as MainActivity).binding.searchPlayContainer.isVisible = true
                (activity as MainActivity).showVideo()

            })
        binding.searchRv.adapter = searchRecyclerViewAdapter
        binding.searchRv.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    //검색 함수 : 검색어 변화를 감지해 검색하고 검색결과 변화를 감지해 리사이클러뷰에 반영
    private fun searchWithWord() {
        if (binding.searchEt.text.toString().isBlank()) {
            Toast.makeText(requireContext(), "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show()
        } else {
            searchViewModel.updateSearchWord(binding.searchEt.text.toString())
            hideKeyboard()
            //검색어 변화 감지
            searchViewModel.searchWordLiveData.observe(viewLifecycleOwner) {
                searchViewModel.getSearchVideoResponse()
            }
            //검색결과 변화 감지 및 리사이클러뷰 반영
            searchViewModel.searchVideoListLiveData.observe(viewLifecycleOwner) {
                searchRecyclerViewAdapter.submitList(
                    searchViewModel.searchVideoListLiveData.value
                )
            }
        }
    }

    //디테일 프래그먼트로 이동하는 함수 : 클릭시
    private fun moveTotDetailFragment() {
        val mainActivity = activity as MainActivity
//        mainActivity.moveToVideoPlayFragment()
    }




    //추천 프래그먼트 호출 함수 : 추천버튼 클릭시 프래그먼트로 이동
    private fun recommendPlace() {
        childFragmentManager.beginTransaction()
            .add(R.id.search_recommend_container, SearchRecommendFragment())
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .commit()
    }






    private fun scrollEndListener() {
        binding.searchRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (searchViewModel.loadingLiveData.value == false) {
                    if (!binding.searchRv.canScrollVertically(1)) {
                        searchViewModel.getNextSearchVideoResponse()
                    }
                }
            }
        })
    }


    //키보드 숨기는 함수
    private fun hideKeyboard() {
        val manager = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(binding.searchEt.windowToken, 0)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}


