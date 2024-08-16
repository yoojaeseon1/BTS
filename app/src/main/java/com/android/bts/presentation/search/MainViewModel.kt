package com.android.bts.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MainViewModel : ViewModel() {
    //비디오 재생
    private val _videoPlayLiveData = MutableLiveData<SnippetEntity>()
    val videoPlayLiveData: LiveData<SnippetEntity> = _videoPlayLiveData



    //재생할 동영상을 업데이트해주는 함수
    fun updateVideoPlayer(video: SnippetEntity) {
        _videoPlayLiveData.value = video
    }

    }
