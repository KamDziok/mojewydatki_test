package com.example.mojewydatki.ui.kategorie

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mojewydatki.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.app_bar_main.*

class KategorieFragment : Fragment() {

    private lateinit var kategorieViewModel: KategorieViewModel

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        kategorieViewModel =
                ViewModelProviders.of(this).get(KategorieViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_kategorie_rc, container, false)

        //Obsluga FloatingActionButton (tego plusa) ukrycie
        val fab = activity!!.fab as? FloatingActionButton
        fab!!.visibility = View.INVISIBLE
        /*
        val textView: TextView = root.findViewById(R.id.text_kategorie)
        kategorieViewModel.text.observe(this, Observer {
            textView.text = it
        })

      */
        return root
    }
}