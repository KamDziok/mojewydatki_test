package com.example.mojewydatki.ui.podsumowanie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mojewydatki.R

class PodsumowanieFragment : Fragment() {

    private lateinit var podsumowanieViewModel: PodsumowanieViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        podsumowanieViewModel =
                ViewModelProviders.of(this).get(PodsumowanieViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_podsumowanie, container, false)

        return root
    }
}