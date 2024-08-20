package com.android.bts.presentation.search

import android.net.http.HttpException
import android.os.Build
import android.os.ext.SdkExtensions
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.android.bts.data.remote.SearchRepositoryImpl
import com.android.bts.network.RetrofitClient
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.net.SocketException
import java.net.UnknownHostException

private const val TAG = "SearchViewModel"



class SearchViewModel(private val searchRepository: SearchRepository) :
    ViewModel() {

    // 예외처리
    private val _fetchState = MutableLiveData<Throwable>()
    val fetchState : LiveData<Throwable>
        get() = _fetchState

    //코루틴 예외처리 핸들러
    private val exceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()

        if (SdkExtensions.getExtensionVersion(Build.VERSION_CODES.S) >= 7) {
            when (throwable) {
                is SocketException -> _fetchState.postValue(throwable)
                is HttpException -> _fetchState.postValue(throwable)
                is UnknownHostException -> _fetchState.postValue(throwable)
                else -> _fetchState.postValue(throwable)
            }
        }
    }



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

    //추천 여행지 인덱스번호
    private val _recommendNumberData = MutableLiveData(0)
    val recommendNumberData = _recommendNumberData

    //
    private val _recommendViewData = MutableLiveData("first")
    val recommendViewData = _recommendViewData

    //
    private val _recommendButtonData = MutableLiveData("left")
    val recommendButtonData = _recommendButtonData


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
        viewModelScope.launch(exceptionHandler) {
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
            viewModelScope.launch(exceptionHandler) {
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

    //추천 여행지 다음 페이지번호를 담는 함수
    fun nextRecommendNumber() {
        _recommendNumberData.value = _recommendNumberData.value?.plus(1)
    }

    //추천 여행지 이전 페이지번호를 담는 함수
    fun previousRecommendNumber() {
        _recommendNumberData.value = _recommendNumberData.value?.minus(1)
    }

    //추천 여행지 페이지번호 예외처리해주는 함수
    fun setRecommendNumber(listSize: Int) {
        if (_recommendNumberData.value!! > -1) _recommendNumberData.value = 0
        else _recommendNumberData.value = listSize - 1
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