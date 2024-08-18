package com.example.app.save

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.bts.R


class SaveFragment : Fragment() {

    private lateinit var savedVideoAdapter: SavedVideoAdapter
    private val viewModel: SavedVideoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.save_fragment, container, false)


        val recyclerView = view.findViewById<RecyclerView>(R.id.savedRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 2) // 2줄의 그리드 레이아웃
        savedVideoAdapter = SavedVideoAdapter(emptyList())
        recyclerView.adapter = savedVideoAdapter


        viewModel.savedVideos.observe(viewLifecycleOwner) { savedVideos ->
            savedVideoAdapter.updateSavedVideos(savedVideos)
        }

        return view
    }
}
