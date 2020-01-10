package com.example.mojewydatki.ui.home

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.text.Editable
import androidx.core.content.contentValuesOf

object TworzenieTabeliWydatek {

    const val SQL_CREATE_TABLE: String =
        "CREATE TABLE WYDATKI (" +
            "ID_WYDATKU INTEGER PRIMARY KEY," +
            "Tytul TEXT NOT NULL," +
            "Kategoria TEXT NOT NULL," +
            "Data TEXT NOT NULL," +
            "Kwota DOUBLE NOT NULL,"+
            "Konto TEXT NOT NULL," +
            "Notatka TEXT NOT NULL,"+
            "Rodzaj INTEGER NOT NULL)"

    const val SQL_DELETE_TABLE = "DROP TABLE IF EXIST WYDATKI"
}

object TworzenieTabeliKonta {

    const val SQL_CREATE_TABLE: String =
        "CREATE TABLE KONTA (" +
                "ID_KONTA INTEGER PRIMARY KEY," +
                "Konto TEXT NOT NULL,"+
                "Kwota DOUBLE NOT NULL)"

    const val SQL_DELETE_TABLE = "DROP TABLE IF EXIST KONTA"
}

object TworzenieTabeliKategori {

    const val SQL_CREATE_TABLE: String =
        "CREATE TABLE KATEGORIE (" +
                "ID_KATEGORI INTEGER PRIMARY KEY," +
                "Kategoria TEXT NOT NULL,"

    const val SQL_DELETE_TABLE = "DROP TABLE IF EXIST KONTA"
}

class PayDataBase(context: Context) : SQLiteOpenHelper(context, "WYDATKI", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL (TworzenieTabeliWydatek.SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL (TworzenieTabeliWydatek.SQL_DELETE_TABLE)
        onCreate(db)
    }

    fun dodajWydatek(title: String, category: String, date: String, value: Double, acount: String, note: String, radio: Int){
        val db: SQLiteDatabase = getWritableDatabase()
        val row: ContentValues = ContentValues()
        row.put("Rodzaj", radio)
        row.put("Notatka", note)
        row.put("Data", date)
        row.put("Konto", acount)
        row.put("Kwota", value)
        row.put("Tytul", title)
        row.put("Kategoria", category)
        db.insertOrThrow("WYDATKI", null, row)
    }
}

class AcountDataBase(context: Context) : SQLiteOpenHelper(context, "KONTA", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL (TworzenieTabeliKonta.SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL (TworzenieTabeliKonta.SQL_DELETE_TABLE)
        onCreate(db)
    }

    fun dodajKonto(acount: String, value: Double){
        val db: SQLiteDatabase = getWritableDatabase()
        val row: ContentValues = ContentValues()
        row.put("Konto", acount)
        row.put("Kwota", value)
        db.insertOrThrow("KONTA", null, row)
    }
}

class CategoryDataBase(context: Context) : SQLiteOpenHelper(context, "KATEGORIE", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL (TworzenieTabeliKategori.SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL (TworzenieTabeliKategori.SQL_DELETE_TABLE)
        onCreate(db)
    }

    fun dodajKonto(category: String){
        val db: SQLiteDatabase = getWritableDatabase()
        val row: ContentValues = ContentValues()
        row.put("Kategoria", category)
        db.insertOrThrow("KATEGORIE", null, row)
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