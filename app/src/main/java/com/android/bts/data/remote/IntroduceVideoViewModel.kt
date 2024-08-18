package com.android.bts.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.bts.data.remote.CommentRepository
import com.android.bts.data.remote.CommentItem
import kotlinx.coroutines.launch

class IntroduceVideoViewModel(private val repository: CommentRepository) : ViewModel() {
    private val _comments = MutableLiveData<List<CommentItem>>()
    val comments: LiveData<List<CommentItem>> get() = _comments

    fun fetchComments(videoId: String, apiKey: String) {
        viewModelScope.launch {
            val comments = repository.getVideoComments(videoId, apiKey)
            _comments.postValue(comments)
        }
    }
}
