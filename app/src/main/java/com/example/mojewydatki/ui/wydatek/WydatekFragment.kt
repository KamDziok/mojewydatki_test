package com.example.mojewydatki.ui.wydatek

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mojewydatki.R
import kotlinx.android.synthetic.main.fragment_wydatek.*
import java.util.*
import android.content.Context
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.mojewydatki.ui.home.PayDataBase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.app_bar_main.*
import java.text.NumberFormat


class WydatekFragment : Fragment() {

    private lateinit var wydatekViewModel: WydatekViewModel

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        wydatekViewModel =
                ViewModelProviders.of(this).get(WydatekViewModel::class.java)
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
            val title: String = activity!!.categoryTitle_textedit.getText().toString()
            val saldoString  = activity!!.konto_saldop_textedit.getText().toString()
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
                    db.dodajWydatek(title, category, day, saldo, acount, note, radio)

                    //czyszczenie formularza
                    activity!!.categoryTitle_textedit.setText("")
                    activity!!.konto_saldop_textedit.setText("")
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
}