package com.example.mojewydatki.ui.przeglad

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
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

class PrzegladFragment : Fragment() {



    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_przeglad, container, false)

        //Obsluga FloatingActionButton (tego plusa) ukrycie
        val fab = activity!!.fab as? FloatingActionButton
        fab!!.visibility = View.INVISIBLE

        //Obsluga bazy danych
        var dbP = PayDataBase (activity!!.applicationContext)
        val db = dbP.writableDatabase

        //Obsluga wyswietlania moich_wydatkow na stronie glownej
        val recyclerView= root.findViewById<RecyclerView>(R.id.przeglad_rc)
        recyclerView.layoutManager= LinearLayoutManager(activity)
        var adapter = PrzegladAdapter(db)
        adapter.setListPrzeglad(dbP.getKategorieAndSuma())
        recyclerView.adapter =  adapter

        return root
    }
}