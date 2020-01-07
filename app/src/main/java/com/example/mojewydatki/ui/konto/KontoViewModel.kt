package com.example.mojewydatki.ui.konto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class KontoViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Dodawanie/edycja konta lub wszystkie konta jeśli nie uda się przenieść wszytskich kont do menu bocznego"
    }
    val text: LiveData<String> = _text
}