package com.example.mojewydatki.ui.kategorie

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mojewydatki.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.app_bar_main.*
import com.example.mojewydatki.ui.home.PayDataBase
import kotlinx.android.synthetic.main.fragment_konto.*
import java.text.NumberFormat
import android.util.Log
import android.view.Window
import android.widget.Button
import com.example.mojewydatki.ui.home.CategoryDataBase

class KategorieFragment : Fragment() {
    internal lateinit var btn : Button
    internal lateinit var myDialog : Dialog
    internal lateinit var txt : TextView

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val root = inflater.inflate(R.layout.fragment_kategorie_rc, container, false)
        btn = root.findViewById<View>(R.id.dodaj_kategorie) as Button
        btn.setOnClickListener{

            ShowDialog()
        }
        //Obsluga FloatingActionButton (tego plusa) ukrycie
        val fab = activity!!.fab as? FloatingActionButton
        fab!!.visibility = View.INVISIBLE

        /*
        root.findViewById<View>(R.id.dodaj_konto_button)!!.setOnClickListener{    //listener przycisku dodawania konta
            var mesage: Toast
            val title: String = activity!!.categoryTitle_textedit.getText().toString()

            if(title.isNotEmpty()){
                try {
                    val db = CategoryDataBase(activity!!)
                    db.dodajKonto(title)
                    mesage = Toast.makeText(activity!!.applicationContext, "Pomyślnie dodano", Toast.LENGTH_SHORT)
                    mesage.show()
                }catch (e: Exception) {
                    Log.d("Baza", e.message)
                    mesage = Toast.makeText(activity!!.applicationContext, "Coś poszło nie tak", Toast.LENGTH_SHORT)
                    mesage.show()
                }
            }else{
                mesage = Toast.makeText(activity!!.applicationContext, "Podaj wszystkie dane", Toast.LENGTH_SHORT)
                mesage.show()
            }
        }
        */

        return root
    }
    fun ShowDialog(){

        myDialog = Dialog(activity!!)
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        myDialog.setContentView(R.layout.fragment_kategorie)


        txt = myDialog.findViewById<View>(R.id.dodaj_kategorie_button) as TextView
        txt.isEnabled = true
        txt.setOnClickListener{

            Toast.makeText(activity!!.applicationContext,"toast", Toast.LENGTH_LONG).show()
            myDialog.cancel()
        }
        myDialog.show()
    }
}