package com.example.mojewydatki.ui.wydatek

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WydatekViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Tworzenie i edycja wydatku"
    }
    val text: LiveData<String> = _text
}