package com.example.mojewydatki.ui.konto_

import android.app.Dialog
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mojewydatki.R

class Konto_Fragment : Fragment() {
    internal lateinit var btn : Button
    internal lateinit var myDialog : Dialog
    internal lateinit var txt : TextView

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_konto_rc, container, false)

        btn = root.findViewById<View>(R.id.dodaj_konto) as Button
        btn.setOnClickListener{

            ShowDialog()
        }

        return root
    }

    fun ShowDialog(){

        myDialog = Dialog(activity!!)
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        myDialog.setContentView(R.layout.fragment_konto)


        txt = myDialog.findViewById<View>(R.id.dodaj_konto_button) as TextView
        txt.isEnabled = true
        txt.setOnClickListener{

            Toast.makeText(activity!!.applicationContext,"toast", Toast.LENGTH_LONG).show()
            myDialog.cancel()
        }
        myDialog.show()
    }
}