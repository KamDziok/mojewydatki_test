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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mojewydatki.R
import com.example.mojewydatki.ui.home.PayDataBase
import kotlinx.android.synthetic.main.fragment_konto.*
import java.text.NumberFormat

class Konto_Fragment : Fragment() {
    internal lateinit var btn : com.google.android.material.floatingactionbutton.FloatingActionButton
    internal lateinit var myDialog : Dialog
    internal lateinit var txt : TextView

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_konto_rc, container, false)

        //Obsluga bazy danych
        val dbK = PayDataBase (activity!!.applicationContext)
        val db = dbK.writableDatabase

        //Obsluga wyswietlania moich_kont
        val recyclerView: RecyclerView = root.findViewById(R.id.konto_rc)
        recyclerView.layoutManager= LinearLayoutManager(activity)
        recyclerView.adapter =Konto_Adapter(db, { partItem : Konto -> kontoClicked(partItem) })

        btn = root.findViewById<View>(R.id.dodaj_konto) as com.google.android.material.floatingactionbutton.FloatingActionButton
        btn.setOnClickListener{

            ShowDialog()
        }

        return root
    }

    fun kontoClicked(konto: Konto){
        var mesage: Toast

        myDialog = Dialog(activity!!)
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        myDialog.setContentView(R.layout.fragment_kategorie)

        myDialog.show()
        val buttonEdit: Button = myDialog.dodaj_konto_button
        buttonEdit.text = "Edtytuj"
        val buttonDel: Button = myDialog.anuluj_konto_button
        buttonDel.text = "Usuń"

        myDialog.konto_nazwa_textedit.setText(konto.nazwaKonta)
        myDialog.kat_nazwa_textedit.setText(konto.saldo.toString())
    }

    fun ShowDialog(){

        myDialog = Dialog(activity!!)
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        myDialog.setContentView(R.layout.fragment_konto)


        txt = myDialog.findViewById<View>(R.id.dodaj_konto_button) as TextView
        txt.isEnabled = true
        myDialog.show()
        myDialog.findViewById<View>(R.id.anuluj_konto_button)!!.setOnClickListener{

            myDialog.cancel()
        }
        myDialog.findViewById<View>(R.id.dodaj_konto_button)!!.setOnClickListener{
            var mesage: Toast
            val kontoNazwa: String = myDialog.konto_nazwa_textedit.getText().toString()
            val kontoSaldoS: String = myDialog.kat_nazwa_textedit.getText().toString()

            if(kontoNazwa.isNotEmpty() && kontoSaldoS.isNotEmpty()) {
                try{
                    val nf = NumberFormat.getInstance()
                    val saldo = nf.parse(kontoSaldoS).toDouble()

                    val db: PayDataBase = PayDataBase(activity!!)
                    db.dodajKonto(kontoNazwa, saldo)

                    mesage = Toast.makeText(activity!!.applicationContext, "Pomyślnie dodano", Toast.LENGTH_LONG)
                    mesage.show()

                    //czyszczenie formularza
                    myDialog.konto_nazwa_textedit.setText("")
                    myDialog.kat_nazwa_textedit.setText("")
                    myDialog.cancel()
                }catch (e: Exception){
                    mesage = Toast.makeText(activity!!.applicationContext, "Coś poszło nie tak", Toast.LENGTH_LONG)
                    mesage.show()
                }
            }else{
                mesage = Toast.makeText(activity!!.applicationContext, "Podaj wszystkie dane", Toast.LENGTH_LONG)
                mesage.show()
            }
        }
    }
}