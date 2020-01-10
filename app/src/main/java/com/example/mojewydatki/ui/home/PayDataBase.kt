package com.example.mojewydatki.ui.home

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.text.Editable
import androidx.core.content.contentValuesOf
import com.example.mojewydatki.ui.wydatek.Contact

object TworzenieTabeliWydatek {

    const val SQL_CREATE_TABLE: String =
        "CREATE TABLE WYDATKI (" +
            "ID_WYDATKU INTEGER PRIMARY KEY," +
            "Tytul TEXT NOT NULL," +
            "ID_KATEGORII INTEGER NOT NULL," +
            "Data TEXT NOT NULL," +
            "Kwota DOUBLE NOT NULL,"+
            "ID_KONTA INTEGER NOT NULL," +
            "Notatka TEXT NOT NULL,"+
            "Rodzaj INTEGER NOT NULL," +
            "FOREIGN KEY(ID_KATEGORII) REFERENCES KATEGORIE(ID_KATEGORII)," +
            "FOREIGN KEY(ID_KONTA) REFERENCES KONTO(ID_KONTA))"

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
                "ID_KATEGORII INTEGER PRIMARY KEY," +
                "Kategoria TEXT NOT NULL)"

    const val SQL_DELETE_TABLE = "DROP TABLE IF EXIST KONTA"
}

class PayDataBase(context: Context) : SQLiteOpenHelper(context, "WYDATKI", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL (TworzenieTabeliKonta.SQL_CREATE_TABLE)
        db?.execSQL (TworzenieTabeliKategori.SQL_CREATE_TABLE)
        db?.execSQL (TworzenieTabeliWydatek.SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL (TworzenieTabeliWydatek.SQL_DELETE_TABLE)
        db?.execSQL (TworzenieTabeliKategori.SQL_DELETE_TABLE)
        db?.execSQL (TworzenieTabeliKonta.SQL_DELETE_TABLE)
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

    fun dodajWydatek2(title: String, category: Int, date: String, value: Double, acount: Int, note: String, radio: Int){
        val db: SQLiteDatabase = getWritableDatabase()
        val row: ContentValues = ContentValues()
        row.put("Rodzaj", radio)
        row.put("Notatka", note)
        row.put("Data", date)
        row.put("ID_KONTA", acount)
        row.put("Kwota", value)
        row.put("Tytul", title)
        row.put("ID_KATEGORII", category)
        db.insertOrThrow("WYDATKI", null, row)
    }

    fun getIDKonta(name: String): Contact? {
        val db = this.writableDatabase
        val selectQuery = "SELECT  ID_KONTA FROM Konta WHERE Konto = ?"
        db.rawQuery(selectQuery, arrayOf(name)).use { // .use requires API 16
            if (it.moveToFirst()) {
                val result = Contact()
                result.id = it.getInt(it.getColumnIndex("ID_KONTA"))
                return result
            }
        }
        return null
    }

    fun getIDKat(name: String): Contact? {
        val db = this.writableDatabase
        val selectQuery = "SELECT  ID_KATEGORII FROM KATEGORIE WHERE Kategoria = ?"
        db.rawQuery(selectQuery, arrayOf(name)).use { // .use requires API 16
            if (it.moveToFirst()) {
                val result = Contact()
                result.id = it.getInt(it.getColumnIndex("ID_KATEGORII"))
                return result
            }
        }
        return null
    }

    fun dodajKonto(acount: String, value: Double){
        val db: SQLiteDatabase = getWritableDatabase()
        val row: ContentValues = ContentValues()
        row.put("Konto", acount)
        row.put("Kwota", value)
        db.insertOrThrow("KONTA", null, row)
    }

    fun dodajKategorie(category: String){
        val db: SQLiteDatabase = getWritableDatabase()
        val row: ContentValues = ContentValues()
        row.put("Kategoria", category)
        db.insertOrThrow("KATEGORIE", null, row)
    }

//    fun getKatSaldo(idKat: Int): Int {
//        val db = this.writableDatabase
//        val selectQuery = "SELECT SUM(Kwota) FROM KATEGORIE WHERE WYDATKI = ?"
//        db.rawQuery(selectQuery, arrayOf(idKat)).use { // .use requires API 16
//            if (it.moveToFirst()) {
//                val result = Contact()
//                result.id = it.getInt(it.getColumnIndex("ID_KATEGORII"))
//                return result
//            }
//        }
//        return 0
//    }
}

/*
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
*/
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