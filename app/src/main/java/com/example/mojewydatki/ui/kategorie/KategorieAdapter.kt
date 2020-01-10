package com.example.mojewydatki.ui.kategorie

import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mojewydatki.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.pojedynczy_przeglad_row.view.*

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
            var kategoria = Kategoria(cursor.getInt(0), cursor.getString(1))
            this.partItemList.add(kategoria)
            (holder as ViewHolder).bind(partItemList[position], clickListener)
            katSaldo.setText("")
        }
    }

    inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
        LayoutContainer{
        fun bind(part: Kategoria, clickListener: (Kategoria) -> Unit) {
            containerView.nazwa_kategorii_poj.text = part.nazwaKat
            containerView.setOnClickListener { clickListener(part)}
        }
    }
}