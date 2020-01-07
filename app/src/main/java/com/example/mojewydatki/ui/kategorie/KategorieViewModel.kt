package com.example.mojewydatki.ui.kategorie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class KategorieViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Kategorie"
    }
    val text: LiveData<String> = _text
}