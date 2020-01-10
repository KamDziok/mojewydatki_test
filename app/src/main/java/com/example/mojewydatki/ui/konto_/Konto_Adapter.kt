package com.example.mojewydatki.ui.konto_


import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mojewydatki.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.pojedyncze_konto_row.*
import kotlinx.android.synthetic.main.pojedyncze_konto_row.view.*

class Konto_Adapter(val db: SQLiteDatabase) : RecyclerView.Adapter<Konto_Adapter.ViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val payRow = layoutInflater.inflate(R.layout.pojedyncze_konto_row,viewGroup, false)
        return ViewHolder(payRow)
    }

    override fun getItemCount(): Int {
        val cursor = db.query("KONTA", null,
            null, null,
            null, null, null)
        val countRow = cursor.count
        cursor.close()
        return countRow
        //PayDataBase.payTitle.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val accountName = holder.containerView.nazwa_kategorii_poj
        val accountSaldo = holder.containerView.bilans_dla_kategorii_oddaty_poj

        val cursor = db.query("KONTA", null,
            "ID_KONTA" + "=?", arrayOf(holder.adapterPosition.plus(1).toString()),
            null, null, null)

        if(cursor.moveToFirst()){
            accountName.setText(cursor.getString(1))
            accountSaldo.setText(cursor.getString(2))
        }
    }
    inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer
}