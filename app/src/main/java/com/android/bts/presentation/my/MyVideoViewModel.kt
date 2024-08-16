package com.android.bts.presentation.my

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyVideoViewModel() : ViewModel() {

    //LiveData를 이용하여 체크박스 텍스트상태저장
    private val _checkedText = MutableLiveData<MutableList<String>>()
    val checkedText: LiveData<MutableList<String>> get() = _checkedText

    //체크박스 상태를 업데이트하는 함수
    fun updateChecked(text: String, isChecked: Boolean) {
        val currentText = _checkedText.value ?: mutableListOf()

        if (isChecked) {
            if (!currentText.contains(text)) {
                currentText.add(text)
            }
        } else {
            currentText.remove(text)
        }
        _checkedText.value = currentText
    }
}