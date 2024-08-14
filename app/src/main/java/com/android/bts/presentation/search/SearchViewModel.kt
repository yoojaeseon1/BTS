package com.android.bts.presentation.search

import android.util.Log
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

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading


    //검색어를 업데이트해주는 함수
    fun updateSearchWord(searchWord: String) {
        _searchWordLiveData.value = searchWord
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
    private fun updateSearchVideo(list: SearchVideoEntity) {
        _searchVideoLiveData.value = list.items.map { it.snippet }
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