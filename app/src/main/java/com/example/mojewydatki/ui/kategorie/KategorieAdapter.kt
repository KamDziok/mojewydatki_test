package com.example.mojewydatki.ui.kategorie

import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mojewydatki.R
import com.example.mojewydatki.ui.home.PayDataBase
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.pojedynczy_przeglad_row.view.*
import kotlin.math.log

class Kategoria(id: Int, nazwa: String){
    var id: Int = 0
    var nazwaKat: String = ""
    private var suma: Double = 0.0

    init{
        this.id = id
        this.nazwaKat = nazwa
    }

    public fun setSuma(suma: Double){
       this.suma = suma
    }

    public fun getSuma(): Double{
        return suma
    }
}

class KategorieAdapter(val db: SQLiteDatabase, val clickListener: (Kategoria) -> Unit) : RecyclerView.Adapter<KategorieAdapter.ViewHolder>() {

    var partItemList = ArrayList<Kategoria>()
    var printId = 0
    var przeniesienie = 0

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val payRow = layoutInflater.inflate(R.layout.pojedynczy_przeglad_row,viewGroup, false)
        return ViewHolder(payRow)
    }

    override fun getItemCount(): Int {
        val cursor = db.query("KATEGORIE", null,
            null, null,
            null, null, null)
        val countRow = cursor.count
        cursor.close()
        return countRow
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val katName = holder.containerView.nazwa_kategorii_poj
        val katSaldo = holder.containerView.bilans_dla_kategorii_oddaty_poj
        val allId = this.itemCount
        var iterator = holder.adapterPosition.plus(1)
        iterator += przeniesienie

//        Log.d("onBindViewHolder ", "nowa iteracja")
//        Log.d("onBindViewHolder allId", allId.toString())
//        Log.d("onBindViewHolder iterator", iterator.toString())
//        Log.d("onBindViewHolder printId", printId.toString())
        //Log.d("partItemList adapterPosition", holder.adapterPosition.plus(1).toString())
        var cursor = db.query("KATEGORIE", null,
            "ID_KATEGORII" + "=?", arrayOf(iterator.toString()),
            null, null, null)
        if(!cursor.moveToFirst() || printId < allId){
            while(!cursor.moveToFirst()){
                iterator++
                przeniesienie++
                cursor = db.query("KATEGORIE", null,
                    "ID_KATEGORII" + "=?", arrayOf(iterator.toString()),
                    null, null, null)
            }
        }

        if (cursor.moveToFirst()) {
            var kategoria = Kategoria(cursor.getInt(0), cursor.getString(1))
//            Log.d("partItemList przed", partItemList.size.toString())
//            Log.d("partItemList id", kategoria.id.toString())
//            Log.d("partItemList nazwa", kategoria.nazwaKat)
//            Log.d("partItemList position", position.toString())
            this.partItemList.add(kategoria)
//            Log.d("partItemList po", partItemList.size.toString())
            try {
                (holder as ViewHolder).bind(partItemList[position], clickListener)
            } catch (e: Exception) {
                Log.d("KategoriePrint", e.message)
            }
            katSaldo.setText("")
            printId++
        }
    }

    public fun clearList(){
        partItemList.clear()
    }

    public fun zerujPrintId(){
        printId = 0
        przeniesienie = 0
    }

    inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
        LayoutContainer{
        fun bind(part: Kategoria, clickListener: (Kategoria) -> Unit) {
            containerView.nazwa_kategorii_poj.text = part.nazwaKat
            containerView.bilans_dla_kategorii_oddaty_poj.text = ""
            containerView.setOnClickListener { clickListener(part)}
        }
    }
}