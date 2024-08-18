package com.android.bts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.bts.presentation.search.ItemsEntity


class MainViewModel : ViewModel() {
    //비디오 재생
    private val _videoPlayLiveData = MutableLiveData<ItemsEntity>()
    val videoPlayLiveData: LiveData<ItemsEntity> = _videoPlayLiveData



    //재생할 동영상을 업데이트해주는 함수
    fun updateVideoPlayer(video: ItemsEntity) {
        _videoPlayLiveData.value = video
    }

    }
