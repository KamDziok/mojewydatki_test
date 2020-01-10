package com.example.mojewydatki.ui.konto_


import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mojewydatki.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.pojedyncze_konto_row.view.*

class Konto(id: Int, nazwa: String, saldo: Double){
    var id: Int = 0
    var nazwaKonta: String = ""
    var saldo = 0.0

    init{
        this.id = id
        this.nazwaKonta = nazwa
        this.saldo = saldo
    }
}

class Konto_Adapter(val db: SQLiteDatabase, val clickListener: ((Konto) -> Unit)?) : RecyclerView.Adapter<Konto_Adapter.ViewHolder>(){

    var listaKont = ArrayList<Konto>()

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
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val accountName = holder.containerView.nazwa_kategorii_poj
        val accountSaldo = holder.containerView.bilans_dla_kategorii_oddaty_poj

        val cursor = db.query("KONTA", null,
            "ID_KONTA" + "=?", arrayOf(holder.adapterPosition.plus(1).toString()),
            null, null, null)

        if(cursor.moveToFirst()){
            var konto = Konto(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2))
            listaKont.add(konto)
            if (clickListener != null) {
                (holder as ViewHolder).bind(listaKont[position], clickListener)
            }else{
                holder.containerView.nazwa_kategorii_poj.text = konto.nazwaKonta
                holder.containerView.bilans_dla_kategorii_oddaty_poj.text = konto.saldo.toString()
            }
        }
    }
    inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(part: Konto, clickListener: (Konto) -> Unit) {
            containerView.nazwa_kategorii_poj.text = part.nazwaKonta
            containerView.bilans_dla_kategorii_oddaty_poj.text = part.saldo.toString()
            containerView.setOnClickListener { clickListener(part) }
        }
    }
}