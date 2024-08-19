package com.example.app.save

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.bts.R
import com.android.bts.presentation.detail.VideoDetailFragment

class SavedFragment : Fragment() {

    private lateinit var savedVideoAdapter: SavedVideoAdapter
    private val savedVideoViewModel: SavedVideoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_saved, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.savedRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        savedVideoAdapter = SavedVideoAdapter(emptyList()) { video ->
            navigateToVideoDetailFragment(video)
        }

        recyclerView.adapter = savedVideoAdapter

        savedVideoViewModel.savedVideos.observe(viewLifecycleOwner) { videos ->
            savedVideoAdapter.updateSavedVideos(videos)
        }

        return view
    }

    private fun navigateToVideoDetailFragment(video: SavedVideo) {
        val fragment = VideoDetailFragment.newInstance(video.videoId, video.title)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}

