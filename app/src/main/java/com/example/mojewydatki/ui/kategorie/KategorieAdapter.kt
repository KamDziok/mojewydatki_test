package com.example.mojewydatki.ui.kategorie

import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mojewydatki.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.pojedynczy_przeglad_row.view.*

class KategorieAdapter(val db: SQLiteDatabase) : RecyclerView.Adapter<KategorieAdapter.ViewHolder>() {

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

            katName.setText(cursor.getString(1))
            katSaldo.setText("")
        }
    }

    inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
        LayoutContainer
}