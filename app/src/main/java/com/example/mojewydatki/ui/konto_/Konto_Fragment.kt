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
import kotlinx.android.synthetic.main.fragment_konto.kat_nazwa_textedit
import kotlinx.android.synthetic.main.fragment_konto.konto_nazwa_textedit
import kotlinx.android.synthetic.main.fragment_wydatek.*
import java.text.NumberFormat

class Konto_Fragment : Fragment() {
    internal lateinit var btn : com.google.android.material.floatingactionbutton.FloatingActionButton
    internal lateinit var myDialog : Dialog
    internal lateinit var txt : TextView

    internal lateinit var root: View
    internal lateinit var recyclerView: RecyclerView
    internal lateinit var adapter: Konto_Adapter

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_konto_rc, container, false)

        //Obsluga bazy danych
        val dbK = PayDataBase (activity!!.applicationContext)
        val db = dbK.writableDatabase

        //Obsluga wyswietlania moich_kont
        recyclerView = root.findViewById(R.id.konto_rc)
        recyclerView.layoutManager= LinearLayoutManager(activity)
        adapter = Konto_Adapter(db, { partItem : Konto -> kontoClicked(partItem) })
        adapter.clearList()
        recyclerView.adapter = adapter
        adapter.zerujPrintId()

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
        myDialog.setContentView(R.layout.fragment_konto)

        myDialog.show()
        val buttonEdit: Button = myDialog.dodaj_konto_button
        buttonEdit.text = "Edtytuj"
        val buttonDel: Button = myDialog.anuluj_konto_button
        buttonDel.text = "Usuń"
        myDialog.kat_nazwa_textedit.visibility = View.INVISIBLE
        val saldoTV = myDialog.findViewById<TextView>(R.id.konto_saldop_textView)
        saldoTV.visibility = View.INVISIBLE

        myDialog.konto_nazwa_textedit.setText(konto.nazwaKonta)

        buttonEdit.setOnClickListener {
            if (!myDialog.konto_nazwa_textedit.text.toString().equals(konto.nazwaKonta) && myDialog.konto_nazwa_textedit.text.isNotEmpty()){
                try {
                    val db = PayDataBase(activity!!)
                    db.edytujKonto(konto.id, myDialog.konto_nazwa_textedit.text.toString())
                    mesage = Toast.makeText(activity!!.applicationContext, "Zedytowano wpis", Toast.LENGTH_SHORT)
                    mesage.show()

                    myDialog.cancel()
                } catch (e: Exception) {
                    mesage = Toast.makeText(
                        activity!!.applicationContext,
                        "Coś poszło nie tak",
                        Toast.LENGTH_LONG
                    )
                    mesage.show()
                }
            } else {
                mesage = Toast.makeText(
                    activity!!.applicationContext,
                    "Nie zmieniłeś żadnych danych",
                    Toast.LENGTH_LONG
                )
                mesage.show()
            }
            adapter.clearList()
            recyclerView.adapter = adapter
            adapter.zerujPrintId()
        }

        myDialog.kat_nazwa_textedit.setText(konto.saldo.toString())

        buttonDel.setOnClickListener{
            try {
                val db = PayDataBase(activity!!)
                db.usunKonto(konto.id)
                mesage = Toast.makeText(activity!!.applicationContext, "Usunięto", Toast.LENGTH_SHORT)
                mesage.show()

                myDialog.cancel()
            } catch (e: Exception) {
                mesage = Toast.makeText(
                    activity!!.applicationContext,
                    "Coś poszło nie tak",
                    Toast.LENGTH_SHORT
                )
                mesage.show()
            }
            adapter.clearList()
            recyclerView.adapter = adapter
            adapter.zerujPrintId()
        }
        adapter.clearList()
        recyclerView.adapter = adapter
        adapter.zerujPrintId()
    }

    fun ShowDialog(){

        myDialog = Dialog(activity!!)
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        myDialog.setContentView(R.layout.fragment_konto)


        txt = myDialog.findViewById<View>(R.id.dodaj_konto_button) as TextView
        txt.isEnabled = true

        myDialog.kat_nazwa_textedit.visibility = View.VISIBLE
        val saldoTV = myDialog.findViewById<TextView>(R.id.konto_saldop_textView)
        saldoTV.visibility = View.VISIBLE

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

                    adapter.clearList()
                    recyclerView.adapter = adapter
                    adapter.zerujPrintId()

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