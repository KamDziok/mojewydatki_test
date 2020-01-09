package com.example.mojewydatki.ui.home

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.text.Editable
import androidx.core.content.contentValuesOf

object PayBase: BaseColumns{

    const val TABLE_NAME_WYDATEK = "Wydatek"
    const val TABLE_NAME_KONTO = "Konto"
    const val payTitle = "Tytul"
    const val payCategory = "Kategoria"
    const val payDate = "Data"
    const val payValue = "Kwota"
    const val payAcount = "Konto"
    const val payNote = "Notatka"
    const val payRadio = "Rodzaj"

}

object TworzenieTabeliWydatek {

    const val SQL_CREATE_TABLE: String =
        "CREATE TABLE ${PayBase.TABLE_NAME_WYDATEK} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${PayBase.payTitle} TEXT NOT NULL," +
                "${PayBase.payCategory} TEXT NOT NULL," +
                "${PayBase.payDate} TEXT NOT NULL," +
                "${PayBase.payValue} DOUBLE NOT NULL,"+
                "${PayBase.payAcount} TEXT NOT NULL," +
                "${PayBase.payNote} TEXT NOT NULL,"+
                "${PayBase.payRadio} INTEGER NOT NULL)"

    const val SQL_DELETE_TABLE = "DROP TABLE IF EXIST ${PayBase.TABLE_NAME_WYDATEK}"
}

object TworzenieTabeliKonto {

    const val SQL_CREATE_TABLE: String =
        "CREATE TABLE ${PayBase.TABLE_NAME_KONTO} (" +
                "${PayBase.payValue} DOUBLE NOT NULL)"+
                "${PayBase.payAcount} TEXT NOT NULL,"

    const val SQL_DELETE_TABLE = "DROP TABLE IF EXIST ${PayBase.TABLE_NAME_KONTO}"
}

class PayDataBase(context: Context) : SQLiteOpenHelper(context, PayBase.TABLE_NAME_WYDATEK, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL (TworzenieTabeliWydatek.SQL_CREATE_TABLE)
        db?.execSQL (TworzenieTabeliKonto.SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL (TworzenieTabeliWydatek.SQL_DELETE_TABLE)
        onCreate(db)
    }

    fun dodajWydatek(title: String, category: String, date: String, value: Double, acount: String, note: String, radio: Int){
        val db: SQLiteDatabase = getWritableDatabase()
        val row: ContentValues = ContentValues()
        row.put(PayBase.payRadio, radio)
        row.put(PayBase.payNote, note)
        row.put(PayBase.payDate, date)
        row.put(PayBase.payAcount, acount)
        row.put(PayBase.payValue, value)
        row.put(PayBase.payTitle, title)
        row.put(PayBase.payCategory, category)
        db.insertOrThrow(PayBase.TABLE_NAME, null, row)
    }

    fun dodajKonto(acount: String, value: Double){
        val db: SQLiteDatabase = getWritableDatabase()
        val row: ContentValues = ContentValues()
        row.put("Kwota", value)
        row.put("Konto", acount)
    }
}
/*

object PayDataBase {

    var payTitle = arrayListOf<String>(
        "Kaufland",
        "Watkem",
        "Kaufland",
        "Watkem",
        "Kaufland",
        "Watkem",
        "Kaufland",
        "Watkem",
        "Kaufland",
        "Watkem",
        "Kaufland",
        "Watkem",
        "Kaufland",
        "Watkem"
    )

    var payCategory = arrayListOf<String>(
        "supermarket",
        "samochód",
        "supermarket",
        "samochód",
        "supermarket",
        "samochód",
        "supermarket",
        "samochód",
        "supermarket",
        "samochód",
        "supermarket",
        "samochód",
        "supermarket",
        "samochód"
    )

    var payValue = arrayListOf<Double>(
        34.29,
        101.55,
        34.29,
        101.55,
        34.29,
        101.55,
        34.29,
        101.55,
        34.29,
        101.55,
        34.29,
        101.55,
        34.29,
        101.55
    )

    var payDate = arrayListOf<String>(
        "03.01.2020",
        "06.01.2020",
        "03.01.2020",
        "06.01.2020",
        "03.01.2020",
        "06.01.2020",
        "03.01.2020",
        "06.01.2020",
        "03.01.2020",
        "06.01.2020",
        "03.01.2020",
        "06.01.2020",
        "03.01.2020",
        "06.01.2020"
    )
}

*/