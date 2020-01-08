package com.example.mojewydatki.ui.home

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
import com.example.mojewydatki.MainActivity
import com.example.mojewydatki.R
import com.example.mojewydatki.ui.wydatek.WydatekFragment
import kotlinx.android.synthetic.main.app_bar_main.view.*
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val dbH = PayDataBase (activity!!.applicationContext)
        val db = dbH.writableDatabase

        val recyclerView: RecyclerView= root.findViewById(R.id.recyclerview)
        recyclerView.layoutManager=LinearLayoutManager(activity)
        recyclerView.adapter =HomeAdapter(db)

        val fragment_wydatek = WydatekFragment()
        root.findViewById<View>(R.id.fab).setOnClickListener() {
            childFragmentManager.beginTransaction().add(R.id.nav_view,fragment_wydatek).commit()
            root.findViewById<View>(R.id.fab).visibility = View.INVISIBLE
        }
        return root
    }
}