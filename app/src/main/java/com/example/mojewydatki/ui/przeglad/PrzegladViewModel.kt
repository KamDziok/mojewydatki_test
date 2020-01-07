package com.example.mojewydatki.ui.przeglad

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PrzegladViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Przegląd wydatków w danej kategorii"
    }
    val text: LiveData<String> = _text
}