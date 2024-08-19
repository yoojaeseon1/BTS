package com.android.bts.presentation.home

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.android.bts.BTSUtils
import com.android.bts.data.remote.HomeVideoRepositoryImpl
import com.android.bts.network.RetrofitClient
import com.android.bts.presentation.search.ItemsEntity
import kotlinx.coroutines.launch

class HomeViewModel(private val videoRepository: HomeVideoRepository) : ViewModel() {

    private val _interestedVideos = MutableLiveData<MutableList<ItemsEntity>>()
    val interestedVideos get() = _interestedVideos

    private val _interestedSpots = MutableLiveData<List<String>>()
    val interestedSpots get() = _interestedSpots

    private val _hotSpotVideos = MutableLiveData<MutableList<ItemsEntity>>()
    val hotSpotVideos get() = _hotSpotVideos

    private val _newSpotVideos = MutableLiveData<MutableList<ItemsEntity>>()
    val newSpotVideos get() = _newSpotVideos

    val newSpotResults = mutableListOf<ItemsEntity>()

    private val searchKeyword = "한국 여행"
    private var nextPageToken = ""

    fun initViewModel(){
        _interestedVideos.value = mutableListOf()
        _hotSpotVideos.value = mutableListOf()
        _newSpotVideos.value = mutableListOf()
    }

    fun getInterestedVideoList(context: Activity) {

        viewModelScope.launch {

            val interestedSearchKeyword = searchKeyword + _interestedSpots.value?.joinToString(" ")

            val videoList = videoRepository.getInterestedVideoList(interestedSearchKeyword)
            val items = videoList.items
            val itemsEntity = mutableListOf<ItemsEntity>()

            for (item in items) {
//                itemsEntity.add(item.snippet)
                item.snippet.isLike = BTSUtils.isCheckedLike(context, item.id.videoId)

                itemsEntity.add(item)

//                Log.d("HomeViewModel", "${item}")
            }
            _interestedVideos.value = itemsEntity
        }
    }

    fun getHotVideoList(context: Activity) {

        viewModelScope.launch {

            val videoList = videoRepository.getHotVideoList(searchKeyword)
            val items = videoList.items
            val itemsEntity = mutableListOf<ItemsEntity>()

            for (item in items) {
                item.snippet.isLike = BTSUtils.isCheckedLike(context, item.id.videoId)
//                itemsEntity.add(item.snippet)
                itemsEntity.add(item)
            }
            _hotSpotVideos.value = itemsEntity
        }
    }

    fun getNewVideoList(context: Activity) {


        viewModelScope.launch {
            if(_newSpotVideos.value == null) {
                Log.d("HomeViewModel", "before newSpotVideos is null")
                _newSpotVideos.value = mutableListOf()
            }

            val videoList = videoRepository.getNewVideoList(searchKeyword, nextPageToken)
            val items = videoList.items

            nextPageToken = videoList.nextPageToken

            for (item in items) {
                val checkedLike = BTSUtils.isCheckedLike(context, item.id.videoId)
                Log.d("HomeViewModel", "isLike is = ${checkedLike}")
                item.snippet.isLike = checkedLike
                newSpotResults.add(item)
            }

            _newSpotVideos.value = newSpotResults
        }
    }
}

class HomeViewModelFactory : ViewModelProvider.Factory {
    private val repository = HomeVideoRepositoryImpl(RetrofitClient.searchVideo)

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
//        return super.create(modelClass, extras)
        return HomeViewModel(
            repository
        ) as T

    }
}

//
//fun <T> MutableLiveData<T>.notifyObserver(){
//    this.value = this.value
//}