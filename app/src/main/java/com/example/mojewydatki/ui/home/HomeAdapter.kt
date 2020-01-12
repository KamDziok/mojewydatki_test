package com.example.mojewydatki.ui.home

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mojewydatki.R
import com.example.mojewydatki.ui.kategorie.Kategoria
import com.example.mojewydatki.ui.konto_.Konto
import com.example.mojewydatki.ui.wydatek.Contact
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_wydatek.view.*
import kotlinx.android.synthetic.main.pojedynczy_wydatek_row.view.*

class Wydatek(id: Int, tytul: String, data: String, kwota: Double, notatka: String, rodzaj: Int){
    var id: Int = 0
    lateinit var tytul: String
    private lateinit var kategoria: Kategoria
    lateinit var data: String
    var kwota: Double = 0.0
    private lateinit var konto: Konto
    lateinit var notatka: String
    var rodzaj: Int = -1

    init {
        this.id = id
        this.tytul = tytul
        this.data = data
        this.kwota = kwota
        this.notatka = notatka
        this.rodzaj = rodzaj
    }

    public fun setKategoria(kategoria: Kategoria){
        this.kategoria = kategoria
    }

    public fun getKategoria(): Kategoria{
        return kategoria
    }

    public fun setKonto(konto: Konto){
        this.konto = konto
    }

    public fun getKonto(): Konto{
        return konto
    }
}

class HomeAdapter(val db: SQLiteDatabase , val clickListener: (Wydatek) -> Unit) : RecyclerView.Adapter<HomeAdapter.ViewHolder>(){

    var printId = 0
    var przeniesienie = 0
    var listaWydatek = ArrayList<Wydatek>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val payRow = layoutInflater.inflate(R.layout.pojedynczy_wydatek_row,viewGroup, false)
        return ViewHolder(payRow)
    }

    override fun getItemCount(): Int {
        return listaWydatek.size
//        val cursor = db.query(WydatekInfo.TABLE_NAME, null,
//            null, null,
//            null, null, null)
//        val countRow = cursor.count
//        cursor.close()
//        return countRow
        //PayDataBase.payTitle.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var wydatek = listaWydatek.get(position)
        (holder as ViewHolder).bind(wydatek, clickListener)
        /*
        val allId = this.itemCount
        var iterator = holder.adapterPosition.plus(1)
        iterator += przeniesienie

        var cursor = db.query(WydatekInfo.TABLE_NAME, null,
            WydatekInfo.TABLE_COLUMN_ID + "=?", arrayOf(iterator.toString()),
            null, null, null)
        if(!cursor.moveToFirst() || printId < allId){
            while(!cursor.moveToFirst()){
                iterator++
                przeniesienie++
                cursor = db.query(WydatekInfo.TABLE_NAME, null,
                    WydatekInfo.TABLE_COLUMN_ID + "=?", arrayOf(iterator.toString()),
                    null, null, null)
            }
        }

        Log.d("onBindVH ", "nowa iteracja")
        Log.d("onBindVH 0", cursor.getInt(0).toString())
        Log.d("onBindVH 1", cursor.getString(1))
        Log.d("onBindVH 2", cursor.getString(2))
        Log.d("onBindVH 3", cursor.getString(3))
        Log.d("onBindVH 4", cursor.getString(4))
        Log.d("onBindVH 5", cursor.getString(5))
        Log.d("onBindVH 6", cursor.getString(6))
        Log.d("onBindVH 7", cursor.getString(7))

        if(cursor.moveToFirst()){
            var wydatek = Wydatek(cursor.getInt(0), cursor.getString(1), cursor.getString(3),
                cursor.getDouble(4), cursor.getString(6), cursor.getInt(7))

            var idKategorii = cursor.getInt(2)
//            var cursorHelperKat = db.query(KategorieInfo.TABLE_NAME, null,
//                KategorieInfo.TABLE_COLUMN_ID + "=?", arrayOf(idKategorii.toString()),
//                null, null, null)
//            Log.d("cursorHelperKat 0", cursorHelperKat.getInt(0).toString())
//            Log.d("cursorHelperKat 1", cursorHelperKat.getString(1))
            lateinit var kategoria: Kategoria
            for(kategoriaFor in listaKategoii){
                if(kategoriaFor.id == idKategorii){
                     kategoria = kategoriaFor
                    break
                }
            }

            var cursorHelperKonto = db.query(KontoInfo.TABLE_NAME, null,
                KontoInfo.TABLE_COLUMN_ID + "=?", arrayOf(cursor.getInt(5).toString()),
                null, null, null)
            var konto = Konto(cursorHelperKonto.getInt(0), cursorHelperKonto.getString(1), cursorHelperKonto.getDouble(2))

            wydatek.setKategoria(kategoria)
            wydatek.setKonto(konto)

            (holder as ViewHolder).bind(wydatek, clickListener)
            printId++
        }

         */
    }

    public fun setListKategoii(lista: ArrayList<Wydatek>){
        this.listaWydatek = lista
    }

    public fun zerujPrintId(){
        printId = 0
        przeniesienie = 0
    }

    inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer{
        fun bind(wydatek: Wydatek, clickListener: (Wydatek) -> Unit) {
            /*
            containerView.kat_nazwa_textedit.setText(wydatek.tytul)
            containerView.konto_nazwa_textedit.setText(wydatek.kwota.toString())
            containerView.payCategory_textedit.setText(wydatek.getKategoria().nazwaKat)
            containerView.payDate_textedit.setText(wydatek.data)
            containerView.payKonto_textedit.setText(wydatek.getKonto().nazwaKonta)
            containerView.payNotatka_textedit.setText(wydatek.notatka)
            if(wydatek.rodzaj == 0)
                containerView.radioGroup.check(0)
            else
                containerView.radioGroup.check(1)
             */
            containerView.tytul_wydatku_poj.setText(wydatek.tytul)
            containerView.kategoria_wydatku_poj.setText(wydatek.getKategoria().nazwaKat)
            containerView.kwota_wydatku_poj.setText(wydatek.kwota.toString())
            containerView.data_wydatku_poj.setText(wydatek.data)
            containerView.setOnClickListener { clickListener(wydatek)}
        }
    }
}