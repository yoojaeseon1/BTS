package com.example.app.save

data class SavedVideo(
    val videoId: String,
    val title: String,
    val description: String
)

object SavedVideoRepository {

    private val savedVideos = mutableListOf<SavedVideo>()

    fun addVideo(video: SavedVideo) {
        savedVideos.add(video)
    }

    fun getSavedVideos(): List<SavedVideo> = savedVideos
}
