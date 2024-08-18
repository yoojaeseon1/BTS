package com.android.bts.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.android.bts.data.remote.SearchRepositoryImpl
import com.android.bts.network.RetrofitClient
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

private const val TAG = "SearchViewModel"

class SearchViewModel(private val searchRepository: SearchRepository) :
    ViewModel() {

    //검색어
    private val _searchWordLiveData = MutableLiveData("")
    val searchWordLiveData: LiveData<String> = _searchWordLiveData

    //검색결과
    private val _searchVideoListLiveData = MutableLiveData<List<ItemsEntity>>()
    val searchVideoListLiveData: LiveData<List<ItemsEntity>> = _searchVideoListLiveData
    private var searchVideoMutableList = mutableListOf<ItemsEntity>()

    //다음페이지
    private val _nextPageTokenLiveData = MutableLiveData<String>()
//    val nextPageTokenLiveData: LiveData<String> = _nextPageTokenLiveData

    //비디오 재생
    private val _videoToPlayLiveData = MutableLiveData<ItemsEntity>()
//    val videoToPlayLiveData: LiveData<ItemsEntity> = _videoToPlayLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData = _loadingLiveData


    //검색어를 업데이트해주는 함수
    fun updateSearchWord(searchWord: String) {
        _searchWordLiveData.value = searchWord
        _searchVideoListLiveData.value = listOf()
    }

    //재생할 동영상을 업데이트해주는 함수
    fun updateVideoToPlay(video: ItemsEntity) {
        _videoToPlayLiveData.value = video
    }

    //검색결과 응답값을 받아오는 함수
    fun getSearchVideoResponse() {
        viewModelScope.launch {
            _loadingLiveData.value = false
            val list = async {
                searchWordLiveData.value?.let {
                    searchRepository.getSearchVideoList(
                        it
                    )
                }
            }
            list.await()?.let {
                updateSearchVideo(it)
                _nextPageTokenLiveData.value = it.nextPageToken
            }
        }
    }

    //검색결과를 업데이트해주는 함수
    private fun updateSearchVideo(list: VideoEntity) {
        let {
            _searchVideoListLiveData.value = list.items
            searchVideoMutableList =
                _searchVideoListLiveData.value?.toMutableList() ?: mutableListOf()
        }
    }

    //다음페이지 응답값을 받아오는 함수
    fun getNextSearchVideoResponse() {
        if (_nextPageTokenLiveData.value != "") {
            viewModelScope.launch {
                _loadingLiveData.value = false
                val list = async {
                    searchWordLiveData.value?.let { searchWord ->
                        _nextPageTokenLiveData.value?.let { nextPageToken ->
                            searchRepository.getNextSearchVideoList(
                                searchWord = searchWord, nextPageToken = nextPageToken
                            )
                        }
                    }
                }
                list.await()?.let { addSearchVideo(it) }
            }
        }
    }

    //현재 검색결과 다음페이지를 추가해주는 함수
    private fun addSearchVideo(list: VideoEntity) {
        list.items.forEach { searchVideoMutableList += it }
        _searchVideoListLiveData.value = searchVideoMutableList
    }
}

class SearchViewModelFactory : ViewModelProvider.Factory {
    private val repository = SearchRepositoryImpl(RetrofitClient.searchVideo)
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {

        return SearchViewModel(
            repository
        ) as T
    }
}