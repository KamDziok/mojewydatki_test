package com.example.mojewydatki.ui.wydatek

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mojewydatki.R
import kotlinx.android.synthetic.main.fragment_wydatek.*
import java.util.*
import android.content.Context
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.Toast
import com.example.mojewydatki.ui.home.PayDataBase
import com.example.mojewydatki.ui.home.Wydatek
import com.example.mojewydatki.ui.kategorie.Kategoria
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_konto.*
import kotlinx.android.synthetic.main.fragment_wydatek.kat_nazwa_textedit
import kotlinx.android.synthetic.main.fragment_wydatek.konto_nazwa_textedit
import java.text.NumberFormat

class Contact{

    var id: Int = 0

}

class WydatekFragment : Fragment() {


    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_wydatek, container, false)

        //Obsluga FloatingActionButton (tego plusa) ukrycie
        val fab = activity!!.fab as? FloatingActionButton
        fab!!.visibility = View.INVISIBLE

        val payData = root.findViewById<View>(R.id.payDate_textedit)
        payData.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val activity = activity as Context
            val dpd = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener{ view, mYear, mMonth, mDay -> payDate_textedit.setText(""+mDay+"."+(mMonth+1)+"."+mYear)}, year,month,day)
            dpd.show()
        }

        root.findViewById<View>(R.id.dodaj_wydatek_button)!!.setOnClickListener{    //listener przycisku dodawania wydatku
            var mesage: Toast
            val title: String = activity!!.kat_nazwa_textedit.getText().toString()
            val saldoString  = activity!!.konto_nazwa_textedit.getText().toString()
            val category = activity!!.payCategory_textedit.getText().toString()
            val day = activity!!.payDate_textedit.getText().toString()
            val acount = activity!!.payKonto_textedit.getText().toString()
            val note = activity!!.payNotatka_textedit.getText().toString()
            var radio = -1
            if(activity!!.radioButton_wplyw.isChecked()){
                radio = 0
            }
            if(activity!!.radioButton_wydatek.isChecked()){
                radio = 1
            }

            if( title.isNotEmpty() && saldoString.isNotEmpty() && category.isNotEmpty() && !day.equals("kliknij by wybrać datę") && acount.isNotEmpty() && note.isNotEmpty() && radio >= 0){
                try {
                    val db: PayDataBase = PayDataBase(activity!!)
                    val nf = NumberFormat.getInstance()
                    val saldo = nf.parse(saldoString).toDouble()
                    db.dodajKonto(acount, 0.0)
                    val idKonta = db.getIDKonta(acount)!!.id
                    db.dodajKategorie(category)
                    val idKat = db.getIDKat(category)!!.id
                    db.dodajWydatek2(title, idKat, day, saldo, idKonta, note, radio)
//                    db.dodajWydatek(title, category, day, saldo, acount,

                    //czyszczenie formularza
                    activity!!.kat_nazwa_textedit.setText("")
                    activity!!.konto_nazwa_textedit.setText("")
                    activity!!.payCategory_textedit.setText("")
                    activity!!.payDate_textedit.setText("kliknij by wybrać datę")
                    activity!!.payKonto_textedit.setText("")
                    activity!!.payNotatka_textedit.setText("")

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
       // val textView: TextView = root.findViewById(R.id.text_wydatek)
      //  wydatekViewModel.text.observe(this, Observer {
       //     textView.text = it
      //  })
        return root
    }

//    fun wydatekClicked(wydatek: Wydatek){
//        var mesage: Toast
//
//        myDialog = Dialog(activity!!)
//        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        myDialog.setContentView(R.layout.fragment_wydatek)
//
//        myDialog.show()
//        val buttonEdit: Button = myDialog.dodaj_wydatek_button
//        buttonEdit.text = "Edtytuj"
//        val buttonDel: Button = myDialog.anuluj_wydatek_button
//        buttonDel.text = "Usuń"
//
//        myDialog.wydatek_nazwa_textedit.setText(wydatek.nazwaWydatku)
//
//        buttonEdit.setOnClickListener {
//            if (!myDialog.konto_nazwa_textedit.text.toString().equals(wydatek.nazwaKonta) && myDialog.konto_nazwa_textedit.text.isNotEmpty()) {
//                try {
//                    val db = PayDataBase(activity!!)
//                    db.edytujKonto(wydatek.id, myDialog.konto_nazwa_textedit.text.toString())
//                    mesage = Toast.makeText(activity!!.applicationContext, "Zedytowano wpis", Toast.LENGTH_SHORT)
//                    mesage.show()
//                    myDialog.cancel()
//                } catch (e: Exception) {
//                    mesage = Toast.makeText(
//                        activity!!.applicationContext,
//                        "Coś poszło nie tak",
//                        Toast.LENGTH_LONG
//                    )
//                    mesage.show()
//                }
//            } else {
//                mesage = Toast.makeText(
//                    activity!!.applicationContext,
//                    "Nie zmieniłeś żadnych danych",
//                    Toast.LENGTH_LONG
//                )
//                mesage.show()
//            }
//        }
//
//        myDialog.kat_nazwa_textedit.setText(konto.saldo.toString())
//
//        buttonDel.setOnClickListener{
//            try {
//                val db = PayDataBase(activity!!)
//                db.usunKonto(konto.id)
//                mesage = Toast.makeText(activity!!.applicationContext, "Usunięto", Toast.LENGTH_SHORT)
//                mesage.show()
//                myDialog.cancel()
//            } catch (e: Exception) {
//                mesage = Toast.makeText(
//                    activity!!.applicationContext,
//                    "Coś poszło nie tak",
//                    Toast.LENGTH_SHORT
//                )
//                mesage.show()
//            }
//        }
//    }

}