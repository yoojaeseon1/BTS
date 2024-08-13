package com.android.bts.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.bts.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding as FragmentSearchBinding
    private lateinit var searchRecyclerViewAdapter: SearchRecyclerViewAdapter
    private val searchViewModel by viewModels<SearchViewModel> {
        SearchViewModelFactory()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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

        searchViewModel.getVideoList()
    }



}