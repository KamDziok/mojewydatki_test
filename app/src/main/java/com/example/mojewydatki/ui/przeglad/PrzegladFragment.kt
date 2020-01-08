package com.example.mojewydatki.ui.przeglad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mojewydatki.R

class PrzegladFragment : Fragment() {

    private lateinit var przegladViewModel: PrzegladViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        przegladViewModel =
                ViewModelProviders.of(this).get(PrzegladViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_przeglad, container, false)

        return root
    }
}