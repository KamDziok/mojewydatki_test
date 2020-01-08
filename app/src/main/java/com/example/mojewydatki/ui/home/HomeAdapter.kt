package com.example.mojewydatki.ui.home

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mojewydatki.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.pojedynczy_wydatek_row.view.*

class HomeAdapter(val db: SQLiteDatabase) : RecyclerView.Adapter<HomeAdapter.ViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val payRow = layoutInflater.inflate(R.layout.pojedynczy_wydatek_row,viewGroup, false)
        return ViewHolder(payRow)
    }

    override fun getItemCount(): Int {
        val cursor = db.query(PayBase.TABLE_NAME, null,
            null, null,
            null, null, null)
        val countRow = cursor.count
        cursor.close()
        return countRow
        //PayDataBase.payTitle.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val payTitle = holder.containerView.data_podsumowanie
        val payCategory = holder.containerView.podsumowanie_wydatki_view
        val payValue = holder.containerView.saldo_konta
        val payDate = holder.containerView.data_podsumowanie

        val cursor = db.query(PayBase.TABLE_NAME, null,
            BaseColumns._ID + "=?", arrayOf(holder.adapterPosition.plus(1).toString()),
            null, null, null)

        if(cursor.moveToFirst()){
            /*
            if(!(cursor.getString(1).isNullOrEmpty() && cursor.getString(2).isNullOrEmpty() &&
                cursor.getString(3).isNullOrEmpty() && cursor.getString(4).isNullOrEmpty())){
                payTitle.setText(cursor.getString(1))
                payCategory.setText(cursor.getString(2))
                payValue.setText(cursor.getString(3))
                payDate.setText(cursor.getString(4))
            }
            */
            payTitle.setText(cursor.getString(1))
            payCategory.setText(cursor.getString(2))
            payValue.setText(cursor.getString(3))
            payDate.setText(cursor.getString(4))
        }
    /*
            payTitle.setText(PayDataBase.payTitle[position])
            payCategory.setText(PayDataBase.payCategory[position])
            payValue.setText(PayDataBase.payValue[position].toString())
            payDate.setText(PayDataBase.payDate[position])
    */
    }
    inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer
}