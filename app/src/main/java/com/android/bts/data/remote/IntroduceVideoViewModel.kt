package com.android.bts.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.bts.data.remote.CommentRepository
import com.android.bts.data.remote.CommentItem
import kotlinx.coroutines.launch
import android.util.Log

class IntroduceVideoViewModel(private val repository: CommentRepository) : ViewModel() {
    private val _comments = MutableLiveData<List<CommentItem>>()
    val comments: LiveData<List<CommentItem>> get() = _comments

    fun fetchComments(videoId: String, apiKey: String) {
        viewModelScope.launch {
            try {
                Log.d("IntroduceVideoViewModel", "Fetching comments for videoId: $videoId")
                val comments = repository.getVideoComments(videoId, apiKey)
                Log.d("IntroduceVideoViewModel", "Fetched ${comments.size} comments")
                _comments.postValue(comments)
            } catch (e: retrofit2.HttpException) {
                Log.e("IntroduceVideoViewModel", "HTTP error: ${e.code()}, message: ${e.message()}")
            } catch (e: Exception) {
                Log.e("IntroduceVideoViewModel", "Unexpected error", e)
            }
        }
    }
}
