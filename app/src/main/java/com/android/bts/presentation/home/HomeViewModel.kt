package com.android.bts.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.android.bts.data.remote.HomeVideoRepositoryImpl
import com.android.bts.network.RetrofitClient
import com.android.bts.presentation.search.SnippetEntity
import kotlinx.coroutines.launch

class HomeViewModel(private val videoRepository: HomeVideoRepository) : ViewModel() {

    private val _interestedVideos = MutableLiveData<MutableList<SnippetEntity>>()
    val interestedVideos get() = _interestedVideos

    private val _hotSpotVideos = MutableLiveData<MutableList<SnippetEntity>>()
    val hotSpotVideos get() = _hotSpotVideos

    private val _newSpotVideos = MutableLiveData<MutableList<SnippetEntity>>()
    val newSpotVideos get() = _newSpotVideos


    fun getInterestedVideoList() {

        viewModelScope.launch {

            val videoList = videoRepository.getHotVideoList()
            val items = videoList.items?:listOf()
            val snippetEntities = mutableListOf<SnippetEntity>()

            if (items != null) {
                for (item in items) {
                    snippetEntities.add(item.snippet)
                }
            }
            _interestedVideos.value = snippetEntities
        }
    }

    fun getHotSpotVideoList() {

        viewModelScope.launch {

            val videoList = videoRepository.getHotVideoList()
            val items = videoList.items?:listOf()
            val snippetEntities = mutableListOf<SnippetEntity>()

            if (items != null) {
                for (item in items) {
                    snippetEntities.add(item.snippet)
                }
            }
            _hotSpotVideos.value = snippetEntities
        }
    }

    fun getNewVideoList() {

        viewModelScope.launch {

            val videoList = videoRepository.getNewVideoList()
            val items = videoList.items?:listOf()
            val snippetEntities = mutableListOf<SnippetEntity>()

            if (items != null) {
                for (item in items) {
                    snippetEntities.add(item.snippet)
                }
            }
            _newSpotVideos.value = snippetEntities
        }
    }
}

class HomeViewModelFactory : ViewModelProvider.Factory {
    private val repository = HomeVideoRepositoryImpl(RetrofitClient.homeVideo)

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
//        return super.create(modelClass, extras)
        return HomeViewModel(
            repository
        ) as T

    }
}