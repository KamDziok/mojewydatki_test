package com.example.mojewydatki.ui.podsumowanie

import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mojewydatki.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.pojedynczy_podsumowanie_row.view.*

public class Podsumowanie(){
    lateinit var data: String
    var wydatki: Double = 0.0
    var wplywy: Double = 0.0
    var bilans: Double = 0.0
    var kategoria: String = ""
}

public class PodsumowanieAdapter(val db: SQLiteDatabase) : RecyclerView.Adapter<PodsumowanieAdapter.ViewHolder>(){

    private var listaPodsumowanie = ArrayList<Podsumowanie>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PodsumowanieAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val podsumowanieRow = layoutInflater.inflate(R.layout.pojedynczy_podsumowanie_row,parent, false)
        return ViewHolder(podsumowanieRow)
    }

    override fun getItemCount(): Int {
        return listaPodsumowanie.size
    }

    override fun onBindViewHolder(holder: PodsumowanieAdapter.ViewHolder, position: Int) {
        val data = holder.containerView.nazwa_kategorii_poj
        val wydatki = holder.containerView.podsumowanie_wydatki
        val wplywy = holder.containerView.podsumowanie_wplywy
        val bilans = holder.containerView.podsumowanie_bilans
        val kategoria = holder.containerView.podsumowanie_kategoria_max_wydatki

        var podsumowanie = listaPodsumowanie.get(position)

        data.setText(podsumowanie.data)
        wydatki.setText(podsumowanie.wydatki.toString())
        wplywy.setText(podsumowanie.wplywy.toString())
        bilans.setText( (podsumowanie.wplywy + podsumowanie.wydatki).toString() )
        kategoria.setText(podsumowanie.kategoria)
    }

    public fun setListaPodsumownaie(lista: ArrayList<Podsumowanie>){
        this.listaPodsumowanie = lista
    }

    inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
        LayoutContainer
}