package com.example.mojewydatki.ui.przeglad

import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mojewydatki.R
import com.example.mojewydatki.ui.home.HomeAdapter
import com.example.mojewydatki.ui.kategorie.Kategoria
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.pojedynczy_przeglad_row.view.*

class PrzegladAdapter(val db: SQLiteDatabase): RecyclerView.Adapter<PrzegladAdapter.ViewHolder>(){

    private var listPrzeglad = ArrayList<Kategoria>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrzegladAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val payRow = layoutInflater.inflate(R.layout.pojedynczy_przeglad_row,parent, false)
        return ViewHolder(payRow)
    }

    override fun getItemCount(): Int {
        return listPrzeglad.size
    }

    override fun onBindViewHolder(holder: PrzegladAdapter.ViewHolder, position: Int) {
        val kategoriaNazwa = holder.containerView.nazwa_kategorii_poj
        val kategoriaSuma = holder.containerView.bilans_dla_kategorii_oddaty_poj
        var przeglad = listPrzeglad.get(position)
        kategoriaNazwa.text = przeglad.nazwaKat
        kategoriaSuma.text = przeglad.getSuma().toString()
    }

    public fun setListPrzeglad(list: ArrayList<Kategoria>){
        this.listPrzeglad = list
    }

    inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
        LayoutContainer
}