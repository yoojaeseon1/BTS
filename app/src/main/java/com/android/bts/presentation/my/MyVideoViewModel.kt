package com.android.bts.presentation.my

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyVideoViewModel() : ViewModel() {

    //LiveData를 이용하여 체크박스 텍스트상태저장
    private val _checkedText = MutableLiveData<List<String>>(emptyList())
    val checkedText: LiveData<List<String>> get() = _checkedText

    //체크박스 상태를 업데이트하는 함수
    fun updateChecked(items: List<String>) {
        _checkedText.value = items
    }

    //텍스트 변화 반영
    private val _text = MutableLiveData<String>()
    val text: LiveData<String> get() = _text

    fun updateText(newText: String) {
        _text.value = newText
    }
}