//package com.android.bts.presentation.search
//private const val TAG = "HomeViewModel"
//class SearchViewModel(private val repository: VideoRepository = YoutubeRepositoryImpl()) :
//        ViewModel() {
//        private val _trendingVideos = MutableLiveData<List<ListItem.VideoItem>?>()
//        val trendingVideos: LiveData<List<ListItem.VideoItem>?> = _trendingVideos
//
//        fun fetchTrendingVideos(region: String = "US") {
//            viewModelScope.launch {
//                runCatching {
//                    val videos = repository.getTrendingVideos(region).items?.toVideoItem()
//                    _trendingVideos.value = videos
//                }.onFailure {
//                    Log.e(TAG, "fetchTrendingVideos() failed! : ${it.message}")
//                    handleException(it)
//                }
//            }
//        }
//
//        private fun handleException(e: Throwable) {
//            when (e) {
//                is HttpException -> {
//                    val errorJsonString = e.response()?.errorBody()?.string()
//                    Log.e(TAG, "HTTP error: $errorJsonString")
//                }
//
//                is IOException -> Log.e(TAG, "Network error: $e")
//                else -> Log.e(TAG, "Unexpected error: $e")
//            }
//        }
//    }
//
//
//
//}