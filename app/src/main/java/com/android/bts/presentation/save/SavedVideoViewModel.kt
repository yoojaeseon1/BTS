package com.example.app.save

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.bts.presentation.save.LikedVideo
import com.android.bts.presentation.save.SavedVideo

class SavedVideoViewModel : ViewModel() {

    private val _savedVideos = MutableLiveData<List<SavedVideo>>()
    val savedVideos: LiveData<List<SavedVideo>> get() = _savedVideos

    private val _savedLikes = MutableLiveData<List<LikedVideo>>()
    val savedLikes: LiveData<List<LikedVideo>> get() = _savedLikes

    private val savedLikeResults = mutableListOf<LikedVideo>()

    fun saveVideo(video: SavedVideo) {
        _savedVideos.value = _savedVideos.value.orEmpty() + video
    }

    private val _likedVideos = MutableLiveData<List<LikedVideo>>()
    val likedVideos: LiveData<List<LikedVideo>> get() = _likedVideos

    fun likeVideo(video: LikedVideo) {
        _likedVideos.value = _likedVideos.value.orEmpty() + video
    }
}
