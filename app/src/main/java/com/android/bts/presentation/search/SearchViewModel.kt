package com.android.bts.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.android.bts.data.remote.VideoRepositoryImpl
import com.android.bts.network.RetrofitClient
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

private const val TAG = "SearchViewModel"

class SearchViewModel(private val videoRepository: VideoRepository) :
    ViewModel() {





    //검색어
    private val _searchWordLiveData = MutableLiveData("")
    val searchWordLiveData: LiveData<String> = _searchWordLiveData

    //검색결과
    private val _searchVideoLiveData = MutableLiveData<List<SnippetEntity>>()
    val searchVideoLiveData: LiveData<List<SnippetEntity>> = _searchVideoLiveData

    //페이지
    private val _pageLiveData = MutableLiveData("")
    val pageLiveData: LiveData<String> = _pageLiveData

    //비디오 재생
    private val _videoPlayLiveData = MutableLiveData<SnippetEntity>()
    val videoPlayLiveData: LiveData<SnippetEntity> = _videoPlayLiveData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading


    //검색어를 업데이트해주는 함수
    fun updateSearchWord(searchWord: String) {
        _searchWordLiveData.value = searchWord
    }

    //재생할 동영상을 업데이트해주는 함수
    fun updateVideoPlayer(video: SnippetEntity) {
        _videoPlayLiveData.value = video
    }

    //검색결과 응답값을 받아오는 함수
    fun getSearchVideoResponse() {
        viewModelScope.launch {
            _isLoading.value = false
            val list = async { videoRepository.getVideoList(searchWordLiveData.value ?: "") }
            updateSearchVideo(list.await())
        }
    }

    //검색결과를 업데이트해주는 함수
    private fun updateSearchVideo(list: VideoEntity) {
       let { _searchVideoLiveData.value = list.items.map { it.snippet }}
    }

    //다음페이지를 업데이트해주는 함수 : pagetoken 변경
    fun updatePage(pageToken: String) {
        _pageLiveData.value = pageToken
    }

    val page = 0
            val maxPage = 10
    private fun infinityScroll() {

    }


}

class SearchViewModelFactory : ViewModelProvider.Factory {
    private val repository = VideoRepositoryImpl(RetrofitClient.searchVideo)
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {

        return SearchViewModel(
            repository
        ) as T
    }
}