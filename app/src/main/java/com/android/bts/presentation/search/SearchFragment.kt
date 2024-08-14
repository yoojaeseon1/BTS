package com.android.bts.presentation.search

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.android.bts.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding as FragmentSearchBinding
    private lateinit var searchRecyclerViewAdapter: SearchRecyclerViewAdapter
    private val searchViewModel by viewModels<SearchViewModel> {
        SearchViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //리사이클러뷰 어댑터 초기화
        initAdapter()

        //검색어 입력 후 키보드 엔터키
        binding.searchEt.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                searchWithWord()
                return@setOnKeyListener false
            } else return@setOnKeyListener false
        }
        //검색어 입력 후 검색버튼
        binding.searchBtn.setOnClickListener {
            searchWithWord()
        }


    }


    //어댑터 초기화 함수 : 검색결과를 리사이클러뷰로 보여주는 함수. 컨텐츠 클릭시 Detail로 이동.
    private fun initAdapter() {
        searchRecyclerViewAdapter = SearchRecyclerViewAdapter(
            itemClickListener = { item ->
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
            searchViewModel.searchVideoLiveData.observe(viewLifecycleOwner) {
                searchRecyclerViewAdapter.submitList(
                    searchViewModel.searchVideoLiveData.value
                )
            }
        }
    }

    //키보드 숨기는 함수
    private fun hideKeyboard() {
        val manager = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(binding.searchEt.windowToken, 0)
    }

}


