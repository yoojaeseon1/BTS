package com.example.app.save

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.bts.presentation.save.SavedVideo

class SavedVideoViewModel : ViewModel() {

    private val _savedVideos = MutableLiveData<List<SavedVideo>>()
    val savedVideos: LiveData<List<SavedVideo>> get() = _savedVideos

    fun saveVideo(video: SavedVideo) {
        _savedVideos.value = _savedVideos.value.orEmpty() + video
    }
}
