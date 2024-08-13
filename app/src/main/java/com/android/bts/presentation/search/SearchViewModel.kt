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