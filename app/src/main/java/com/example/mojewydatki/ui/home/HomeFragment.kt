package com.example.mojewydatki.ui.home

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mojewydatki.R
import kotlinx.android.synthetic.main.app_bar_main.*
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import android.app.Activity



class HomeFragment : Fragment() {
    internal lateinit var btn : com.google.android.material.floatingactionbutton.FloatingActionButton
    internal lateinit var myDialog : Dialog
    internal lateinit var txt : TextView
    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

         btn = root.findViewById<View>(R.id.dodaj_wydatek) as com.google.android.material.floatingactionbutton.FloatingActionButton
        btn.setOnClickListener{

            ShowDialog()
        }

        //Obsluga bazy danych
        val dbH = PayDataBase (activity!!.applicationContext)
        val db = dbH.writableDatabase

        //Obsluga wyswietlania moich_wydatkow na stronie glownej
        val recyclerView: RecyclerView= root.findViewById(R.id.recyclerview)
        recyclerView.layoutManager=LinearLayoutManager(activity)
        recyclerView.adapter =HomeAdapter(db)

        //Obsluga FloatingActionButton (tego plusa) wyswietlenie
        val fab = activity!!.fab
        fab!!.visibility = View.INVISIBLE
//        val fm = activity!!.supportFragmentManager
//        val fragment_wydatek = WydatekFragment()
        //aktywacja plusa przeładowanie fragmentu na WydatkiFragment i ukrycie plusa
//        fab.setOnClickListener(){
//            //val home = fm.findFragmentByTag("nav_host_fragment")!!
//            //if(home != null)
////            fm.beginTransaction().remove(fm.findFragmentById(R.id.nav_host_fragment)!!.childFragmentManager.fragments.get(0)!!).commit()
//            fab.visibility = View.INVISIBLE
//            fm.beginTransaction().replace(R.id.nav_host_fragment, fragment_wydatek).commit()
//        }

        return root
    }

    fun ShowDialog(){

        myDialog = Dialog(activity!!)
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        myDialog.setContentView(R.layout.fragment_wydatek)


        txt = myDialog.findViewById<View>(R.id.dodaj_wydatek_button) as TextView
        txt.isEnabled = true
        txt.setOnClickListener{

            Toast.makeText(activity!!.applicationContext,"toast", Toast.LENGTH_LONG).show()
            myDialog.cancel()
        }
        myDialog.show()
    }

}