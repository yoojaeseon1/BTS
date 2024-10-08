package com.example.app.save

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.android.bts.BTSUtils
import com.android.bts.R
import com.android.bts.presentation.detail.VideoDetailFragment
import com.android.bts.presentation.save.LikedVideo
import com.android.bts.presentation.save.LikedVideoAdapter
import com.android.bts.presentation.save.SavedVideo
import com.android.bts.presentation.search.Id
import com.android.bts.presentation.search.ItemsEntity
import com.android.bts.presentation.search.SnippetEntity

class SavedFragment : Fragment() {

    private lateinit var savedVideoAdapter: SavedVideoAdapter
    private lateinit var likedVideoAdapter: LikedVideoAdapter
    private val savedVideoViewModel: SavedVideoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_saved, container, false)

        val savedRecyclerView = view.findViewById<RecyclerView>(R.id.savedRecyclerView)
        val likedRectclerView = view.findViewById<RecyclerView>(R.id.likedRecyclerView)
        savedVideoAdapter = SavedVideoAdapter(emptyList()) { video ->
            navigateToVideoDetailFragment(video)
        }

        likedVideoAdapter = LikedVideoAdapter(BTSUtils.selectAllLikes(requireActivity())){ video ->
            navigateToVideoDetailFragment(video)
        }

        savedRecyclerView.adapter = savedVideoAdapter
        likedRectclerView.adapter = likedVideoAdapter

        savedVideoViewModel.savedVideos.observe(viewLifecycleOwner) { videos ->
            savedVideoAdapter.updateSavedVideos(videos)
        }

        savedVideoViewModel.likedVideos.observe(viewLifecycleOwner) { videos ->
            likedVideoAdapter.updateLikedVideos(videos)

            Log.d("SaveFragment", "likedVideos size = ${videos.size}")

        }

        return view
    }

    private fun navigateToVideoDetailFragment(video: SavedVideo) {

        val fragment = VideoDetailFragment.newInstance(ItemsEntity(Id(videoId = video.videoId), SnippetEntity(video.title)))
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_frame, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToVideoDetailFragment(video: LikedVideo) {

        val fragment = VideoDetailFragment.newInstance(ItemsEntity(Id(videoId = video.videoId), SnippetEntity(video.title)))
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_frame, fragment)
            .addToBackStack(null)
            .commit()
    }
}

