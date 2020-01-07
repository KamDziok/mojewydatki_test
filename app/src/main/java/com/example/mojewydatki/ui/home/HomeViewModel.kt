package com.example.mojewydatki.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {


    private val _text = MutableLiveData<String>().apply {
        value = "Tutaj musi być recycler view, w którym będą adaptery pobierane z bazy. Wydatek, kategoria, data i kwota"
    }

    val text: LiveData<String> = _text
}