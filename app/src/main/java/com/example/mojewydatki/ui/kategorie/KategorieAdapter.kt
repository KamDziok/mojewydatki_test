package com.example.mojewydatki.ui.kategorie

import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mojewydatki.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.pojedynczy_przeglad_row.view.*
import kotlin.math.log

class Kategoria(id: Int, nazwa: String){
    var id: Int = 0
    var nazwaKat: String = ""

    init{
        this.id = id
        this.nazwaKat = nazwa
    }
}

class KategorieAdapter(val db: SQLiteDatabase, val clickListener: (Kategoria) -> Unit) : RecyclerView.Adapter<KategorieAdapter.ViewHolder>() {

    var partItemList = ArrayList<Kategoria>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val payRow = layoutInflater.inflate(R.layout.pojedynczy_przeglad_row,viewGroup, false)
        //partItemList.clear()
        return ViewHolder(payRow)
    }

    override fun getItemCount(): Int {
        val cursor = db.query("KATEGORIE", null,
            null, null,
            null, null, null)
        val countRow = cursor.count
        cursor.close()
        return countRow
        //PayDataBase.payTitle.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val katName = holder.containerView.nazwa_kategorii_poj
        val katSaldo = holder.containerView.bilans_dla_kategorii_oddaty_poj

        val cursor = db.query("KATEGORIE", null,
            "ID_KATEGORII" + "=?", arrayOf(holder.adapterPosition.plus(1).toString()),
            null, null, null)

        if(cursor.moveToFirst()){
            Log.d("KategoriePrint", cursor.getInt(0).toString())
            Log.d("KategoriePrint", cursor.getString(1))
            var kategoria = Kategoria(cursor.getInt(0), cursor.getString(1))
            Log.d("partItemList.przed", partItemList.size.toString())
            this.partItemList.add(kategoria)
            Log.d("partItemList.po", partItemList.size.toString())
            try{
                (holder as ViewHolder).bind(partItemList[position], clickListener)
            }catch (e: Exception){
                Log.d("KategoriePrint", e.message)
            }
            katSaldo.setText("")
        }
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