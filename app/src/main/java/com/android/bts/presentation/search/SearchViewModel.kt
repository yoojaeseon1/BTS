package com.android.bts.presentation.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.android.bts.data.remote.VideoRepositoryImpl
import com.android.bts.network.RetrofitClient
import kotlinx.coroutines.launch

private const val TAG = "HomeViewModel"

class SearchViewModel(private val videoRepository: VideoRepository) :
    ViewModel() {

        fun getVideoList() {
            viewModelScope.launch {
                val list = videoRepository.getVideoList()
                Log.d("debug2323",list.toString())
            }
        }
    /*   private val _trendingVideos = MutableLiveData<List<ListItem.VideoItem>?>()
       val trendingVideos: LiveData<List<ListItem.VideoItem>?> = _trendingVideos

       fun fetchTrendingVideos(region: String = "US") {
           viewModelScope.launch {
               runCatching {
                   val videos = repository.getTrendingVideos(region).items?.toVideoItem()
                   _trendingVideos.value = videos
               }.onFailure {
                   Log.e(TAG, "fetchTrendingVideos() failed! : ${it.message}")
                   handleException(it)
               }
           }
       }

       private fun handleException(e: Throwable) {
           when (e) {
               is HttpException -> {
                   val errorJsonString = e.response()?.errorBody()?.string()
                   Log.e(TAG, "HTTP error: $errorJsonString")
               }

               is IOException -> Log.e(TAG, "Network error: $e")
               else -> Log.e(TAG, "Unexpected error: $e")
           }
       }
   }
*/
}
class SearchViewModelFactory : ViewModelProvider.Factory{
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