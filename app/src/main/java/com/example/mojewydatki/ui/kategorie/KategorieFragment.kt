package com.example.mojewydatki.ui.kategorie

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mojewydatki.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.app_bar_main.*
import com.example.mojewydatki.ui.home.PayDataBase
import kotlinx.android.synthetic.main.fragment_konto.*
import java.text.NumberFormat
import android.widget.Button
import android.view.Window
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_kategorie.*
import kotlinx.android.synthetic.main.fragment_konto.kat_nazwa_textedit

class KategorieFragment : Fragment() {
    internal lateinit var btn : com.google.android.material.floatingactionbutton.FloatingActionButton
    internal lateinit var myDialog : Dialog
    internal lateinit var txt : TextView

    internal lateinit var root: View
    internal lateinit var recyclerView: RecyclerView
    internal lateinit var adapter: KategorieAdapter

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_kategorie_rc, container, false)

        btn = root.findViewById<View>(R.id.dodaj_kategorie) as com.google.android.material.floatingactionbutton.FloatingActionButton
        btn.setOnClickListener{

            ShowDialog()
        }

        //Obsluga bazy danych
        val dbK = PayDataBase (activity!!.applicationContext)
        val db = dbK.writableDatabase
        adapter = KategorieAdapter(db, { partItem : Kategoria -> partItemClicked(partItem) })

        //Obsluga wyswietlania moich_kategorie
        recyclerView = root.findViewById(R.id.kategorie_rc)
        recyclerView.layoutManager= LinearLayoutManager(activity)
        adapter.clearList()
        recyclerView.adapter = adapter
        adapter.zerujPrintId()

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

        buttonDel.setOnClickListener{
            try {
                val db = PayDataBase(activity!!)
                db.usunKategorie(partItem.id)
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
    }

    fun ShowDialog(){

        myDialog = Dialog(activity!!)
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        myDialog.setContentView(R.layout.fragment_kategorie)


        txt = myDialog.findViewById<View>(R.id.dodaj_kategorie_button) as TextView
        txt.isEnabled = true

        myDialog.show()

        val buttonDodaj: Button = myDialog.findViewById<Button>(R.id.dodaj_kategorie_button)
        buttonDodaj.text = "Dodaj"
        val buttonAnuluj: Button = myDialog.findViewById<Button>(R.id.anuluj_kategorie_button)
        buttonAnuluj.text = "Anuluj"

        buttonDodaj.setOnClickListener{
            var mesage: Toast
            val katNazwa: String = myDialog.kat_nazwa_textedit.getText().toString()

            if(katNazwa.isNotEmpty()) {
                try{
                    val db: PayDataBase = PayDataBase(activity!!)
                    db.dodajKategorie(katNazwa)

                    mesage = Toast.makeText(activity!!.applicationContext, "Pomyślnie dodano", Toast.LENGTH_SHORT)
                    mesage.show()

                    //czyszczenie formularza
                    myDialog.kat_nazwa_textedit.setText("")
                    myDialog.cancel()
                }catch (e: Exception){
                    mesage = Toast.makeText(activity!!.applicationContext, "Coś poszło nie tak", Toast.LENGTH_SHORT)
                    mesage.show()
                }
            }else{
                mesage = Toast.makeText(activity!!.applicationContext, "Podaj wszystkie dane", Toast.LENGTH_SHORT)
                mesage.show()
            }
            adapter.clearList()
            recyclerView.adapter = adapter
            adapter.zerujPrintId()
        }
        buttonAnuluj.setOnClickListener{
            myDialog.cancel()
        }
    }
}