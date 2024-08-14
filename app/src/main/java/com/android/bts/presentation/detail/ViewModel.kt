package com.android.bts.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MemoViewModel : ViewModel() {

    private val _isEditTextEnabled = MutableLiveData<Boolean>(false)
    val isEditTextEnabled: LiveData<Boolean> get() = _isEditTextEnabled

    private val _memoText = MutableLiveData<String>("")  // 텍스트 상태 추가
    val memoText: LiveData<String> get() = _memoText

    fun toggleEditTextState() {
        _isEditTextEnabled.value = !(_isEditTextEnabled.value ?: false)
    }

    fun setEditTextEnabled(enabled: Boolean) {
        _isEditTextEnabled.value = enabled
    }

    fun setMemoText(text: String) {
        _memoText.value = text
    }
}
