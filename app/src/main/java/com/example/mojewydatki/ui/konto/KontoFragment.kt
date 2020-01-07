package com.example.mojewydatki.ui.konto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mojewydatki.R

class KontoFragment : Fragment() {

    private lateinit var kontoViewModel: KontoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        kontoViewModel =
                ViewModelProviders.of(this).get(KontoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_konto, container, false)
        val textView: TextView = root.findViewById(R.id.text_konto)
        kontoViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}