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
import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import com.example.mojewydatki.ui.kategorie.Kategoria
import com.example.mojewydatki.ui.kategorie.KategorieAdapter
import com.example.mojewydatki.ui.konto_.Konto
import com.example.mojewydatki.ui.konto_.Konto_Adapter
import kotlinx.android.synthetic.main.fragment_konto.*
import kotlinx.android.synthetic.main.fragment_wydatek.*
import kotlinx.android.synthetic.main.fragment_wydatek.kat_nazwa_textedit
import kotlinx.android.synthetic.main.fragment_wydatek.konto_nazwa_textedit
import java.net.DatagramPacket
import java.text.NumberFormat
import java.util.*


class HomeFragment : Fragment() {
    internal lateinit var btn : com.google.android.material.floatingactionbutton.FloatingActionButton
    internal lateinit var btn2 : com.google.android.material.floatingactionbutton.FloatingActionButton
    internal lateinit var btn3 : com.google.android.material.floatingactionbutton.FloatingActionButton
    internal lateinit var myDialog : Dialog
    internal lateinit var myDialog2 : Dialog
    internal lateinit var myDialog3 : Dialog
    internal lateinit var txt : TextView
    internal lateinit var txt2 : TextView
    internal lateinit var txt3 : TextView
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
    //popup z wyborem kategorii w wydatkach
    fun ShowDialog2(){

        myDialog2 = Dialog(activity!!)
        myDialog2.requestWindowFeature(Window.FEATURE_NO_TITLE)
        myDialog2.setContentView(R.layout.fragment_kategorie_rc)
        //Obsluga bazy danych
        val dbK = PayDataBase (activity!!.applicationContext)
        val db = dbK.writableDatabase

        //Obsluga wyswietlania moich_kategorie

        val recyclerView: RecyclerView = myDialog2.findViewById(R.id.kategorie_rc)
        recyclerView.layoutManager= LinearLayoutManager(activity)
        recyclerView.adapter =KategorieAdapter(db, { partItem : Kategoria -> partItemClicked(partItem) })


        txt2 = myDialog.findViewById<View>(R.id.payCategory_textedit) as TextView
        txt2.isEnabled = true
        btn2 = myDialog2.findViewById<View>(R.id.dodaj_kategorie) as com.google.android.material.floatingactionbutton.FloatingActionButton
        btn2.hide()
        myDialog2.show()

    }
    //popup z wyborem kont w wydatkach
    fun ShowDialog3(){

        myDialog3 = Dialog(activity!!)
        myDialog3.requestWindowFeature(Window.FEATURE_NO_TITLE)
        myDialog3.setContentView(R.layout.fragment_konto_rc)
        //Obsluga bazy danych
        val dbK = PayDataBase (activity!!.applicationContext)
        val db = dbK.writableDatabase

        val recyclerView: RecyclerView = myDialog3.findViewById(R.id.konto_rc)
        recyclerView.layoutManager= LinearLayoutManager(activity)
        recyclerView.adapter = Konto_Adapter(db, { partItem : Konto -> kontoClicked(partItem) })


        txt3 = myDialog.findViewById<View>(R.id.payKonto_textedit) as TextView
        txt3.isEnabled = true
        btn3 = myDialog3.findViewById<View>(R.id.dodaj_konto) as com.google.android.material.floatingactionbutton.FloatingActionButton
        btn3.hide()
        myDialog3.show()

    }

    fun ShowDialog(){

        myDialog = Dialog(activity!!)
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        myDialog.setContentView(R.layout.fragment_wydatek)


        txt = myDialog.findViewById<View>(R.id.dodaj_wydatek_button) as TextView
        txt.isEnabled = true

        myDialog.show()

        myDialog.findViewById<View>(R.id.payCategory_textedit)!!.setOnClickListener{

            ShowDialog2()
        }

        myDialog.findViewById<View>(R.id.payKonto_textedit)!!.setOnClickListener{

            ShowDialog3()
        }
        myDialog.findViewById<View>(R.id.anuluj_wydatek_button)!!.setOnClickListener{

            myDialog.cancel()
        }

        val payData = myDialog.findViewById<View>(R.id.payDate_textedit)
        payData.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val activity = activity as Context
            val dpd = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener{ view, mYear, mMonth, mDay -> payDate_textedit.setText(""+mDay+"."+(mMonth+1)+"."+mYear)}, year,month,day)
            dpd.show()
        }

        myDialog.findViewById<View>(R.id.dodaj_wydatek_button)!!.setOnClickListener{    //listener przycisku dodawania wydatku
            var mesage: Toast
            val title: String = myDialog.kat_nazwa_textedit.getText().toString()
            val saldoString  = myDialog.konto_nazwa_textedit.getText().toString()
            val category = myDialog.payCategory_textedit.getText().toString()
            val day = myDialog.payDate_textedit.getText().toString()
            val acount = myDialog.payKonto_textedit.getText().toString()
            val note = myDialog.payNotatka_textedit.getText().toString()
            var radio = -1
            if(myDialog.radioButton_wplyw.isChecked()){
                radio = 0
            }
            if(myDialog.radioButton_wydatek.isChecked()){
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
                    myDialog.kat_nazwa_textedit.setText("")
                    myDialog.konto_nazwa_textedit.setText("")
                    myDialog.payCategory_textedit.setText("")
                    myDialog.payDate_textedit.setText("kliknij by wybrać datę")
                    myDialog.payKonto_textedit.setText("")
                    myDialog.payNotatka_textedit.setText("")

                    mesage = Toast.makeText(activity!!.applicationContext, "Pomyślnie dodano", Toast.LENGTH_SHORT)
                    mesage.show()
                    myDialog.cancel()
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
    }
    private fun partItemClicked(partItem : Kategoria) {
        var mesage: Toast

        myDialog = Dialog(activity!!)
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        myDialog.setContentView(R.layout.fragment_kategorie)

        myDialog.show()
        val buttonEdit: Button = myDialog.findViewById<Button>(R.id.dodaj_kategorie_button)
        buttonEdit.text = "Edtytuj"
        val buttonDel: Button = myDialog.findViewById<Button>(R.id.anuluj_kategorie_button)
        buttonDel.text = "Usuń"

        myDialog.kat_nazwa_textedit.setText(partItem.nazwaKat)

        buttonEdit.setOnClickListener {
            if (!myDialog.kat_nazwa_textedit.text.toString().equals(partItem.nazwaKat) && myDialog.kat_nazwa_textedit.text.isNotEmpty()) {
                try {
                    val db = PayDataBase(activity!!)
                    db.edytujKategorie(partItem.id, myDialog.kat_nazwa_textedit.text.toString())
                    mesage = Toast.makeText(activity!!.applicationContext, "Zedytowano wpis", Toast.LENGTH_SHORT)
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
        }

}

    fun kontoClicked(konto: Konto){
        var mesage: Toast

        myDialog = Dialog(activity!!)
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        myDialog.setContentView(R.layout.fragment_konto_rc)

        myDialog.show()
        val buttonEdit: Button = myDialog.dodaj_konto_button
        buttonEdit.text = "Edtytuj"
        val buttonDel: Button = myDialog.anuluj_konto_button
        buttonDel.text = "Usuń"

        myDialog.konto_nazwa_textedit.setText(konto.nazwaKonta)
        myDialog.kat_nazwa_textedit.setText(konto.saldo.toString())
    }
}