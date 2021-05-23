package com.example.calendar2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShareDataViewModel : ViewModel() {
    private var text = MutableLiveData<String>()

    fun sendText(string: String) {
        text.value = string
    }

    fun getText(): MutableLiveData<String> = text
}