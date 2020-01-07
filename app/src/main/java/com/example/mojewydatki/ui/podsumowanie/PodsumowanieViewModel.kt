package com.example.mojewydatki.ui.podsumowanie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PodsumowanieViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Bilans na minus i na plus w danym miesiącu oraz suma i na jaką kategorię najwięcej wydatków"
    }
    val text: LiveData<String> = _text
}