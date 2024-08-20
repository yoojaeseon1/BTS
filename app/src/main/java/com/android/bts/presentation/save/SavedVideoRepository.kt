package com.example.app.save

import com.android.bts.presentation.save.SavedVideo


object SavedVideoRepository {

    private val savedVideos = mutableListOf<SavedVideo>()

    fun addVideo(video: SavedVideo) {
        savedVideos.add(video)
    }

    fun getSavedVideos(): List<SavedVideo> = savedVideos
}
