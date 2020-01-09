package com.example.mojewydatki.ui.kategorie

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
import com.example.mojewydatki.ui.home.PayDataBase
import kotlinx.android.synthetic.main.fragment_konto.*
import java.text.NumberFormat

class KategorieFragment : Fragment() {

    private lateinit var kategorieViewModel: KategorieViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        kategorieViewModel =
                ViewModelProviders.of(this).get(KategorieViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_kategorie_rc, container, false)
        /*
        val textView: TextView = root.findViewById(R.id.text_kategorie)
        kategorieViewModel.text.observe(this, Observer {
            textView.text = it
        })
      */

        dodaj_konto_button.setOnClickListener{
            var acount = categoryTitle_textedit.text.toString()
            var saldo = konto_saldop_textedit.text.toString()

            if(acount.isNotEmpty() && saldo.isNotEmpty()){
                try {
                    val db: PayDataBase = PayDataBase(this)
                    val nf = NumberFormat.getInstance()
                    val saldo = nf.parse(saldo).toDouble()
                    db.dodajKonto(saldo, acount)
                }catch (e: Exception) {
                    var mesage = Toast.makeText(applicationContext, "Coś poszło nie tak", Toast.LENGTH_SHORT)
                    mesage.show()
                }
            }
        }
        return root
    }
}