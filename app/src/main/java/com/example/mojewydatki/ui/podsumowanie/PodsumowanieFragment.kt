package com.example.mojewydatki.ui.podsumowanie

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mojewydatki.R
import com.example.mojewydatki.ui.home.PayDataBase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.app_bar_main.*

class PodsumowanieFragment : Fragment() {



    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_podsumowanie, container, false)

        //Obsluga FloatingActionButton (tego plusa) ukrycie
        val fab = activity!!.fab as? FloatingActionButton
        fab!!.visibility = View.INVISIBLE

        //Obsluga bazy danych
        val dbH = PayDataBase (activity!!.applicationContext)
        val db = dbH.writableDatabase

        //Obsluga wyswietlania moich_wydatkow na stronie glownej
        val recyclerView= root.findViewById<RecyclerView>(R.id.podsumowanie_rc)
        recyclerView.layoutManager= LinearLayoutManager(activity)
        val adapter = PodsumowanieAdapter(db)
        adapter.setListaPodsumownaie(dbH.creatPodsumowanie())
        recyclerView.adapter = adapter

        return root
    }
}