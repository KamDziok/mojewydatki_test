package com.example.mojewydatki.ui.home

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.text.Editable
import androidx.core.content.contentValuesOf
import com.example.mojewydatki.ui.wydatek.Contact

object WydatekInfo{
    const val TABLE_NAME = "WYDATKI"
    const val TABLE_COLUMN_ID = "ID_WYDATKU"
    const val TABLE_COLUMN_TYTUL = "Tytul"
    const val TABLE_COLUMN_IDKAT = "ID_KATEGORII"
    const val TABLE_COLUMN_DATA = "Data"
    const val TABLE_COLUMN_KWOTA = "Kwota"
    const val TABLE_COLUMN_IDKONTA = "ID_KONTA"
    const val TABLE_COLUMN_NOTATKA = "Notatka"
    const val TABLE_COLUMN_RODZAJ = "Rodzaj"
}

object KategorieInfo{
    const val TABLE_NAME = "KATEGORIE"
    const val TABLE_COLUMN_ID = "ID_KATEGORII"
    const val TABLE_COLUMN_KATEGORIA = "Kategoria"
}

object KontoInfo{
    const val TABLE_NAME = "KONTA"
    const val TABLE_COLUMN_ID = "ID_KONTA"
    const val TABLE_COLUMN_KONTO = "Konto"
    const val TABLE_COLUMN_SALDO = "Kwota"
}

object TworzenieTabeliWydatek {

    const val SQL_CREATE_TABLE: String =
        "CREATE TABLE ${WydatekInfo.TABLE_NAME} (" +
            "${WydatekInfo.TABLE_COLUMN_ID} INTEGER PRIMARY KEY," +
            "${WydatekInfo.TABLE_COLUMN_TYTUL} TEXT NOT NULL," +
            "${KategorieInfo.TABLE_COLUMN_ID} INTEGER NOT NULL," +
            "${WydatekInfo.TABLE_COLUMN_DATA} TEXT NOT NULL," +
            "${WydatekInfo.TABLE_COLUMN_KWOTA} DOUBLE NOT NULL,"+
            "${KontoInfo.TABLE_COLUMN_ID} INTEGER NOT NULL," +
            "${WydatekInfo.TABLE_COLUMN_NOTATKA} TEXT NOT NULL,"+
            "${WydatekInfo.TABLE_COLUMN_RODZAJ} INTEGER NOT NULL," +
            "FOREIGN KEY(${KategorieInfo.TABLE_COLUMN_ID}) REFERENCES ${KategorieInfo.TABLE_NAME}(${KategorieInfo.TABLE_COLUMN_ID})," +
            "FOREIGN KEY(${KontoInfo.TABLE_COLUMN_ID}) REFERENCES ${KontoInfo.TABLE_NAME}(${KontoInfo.TABLE_COLUMN_ID}))"

    const val SQL_DELETE_TABLE = "DROP TABLE IF EXIST WYDATKI"
}

object TworzenieTabeliKonta {

    const val SQL_CREATE_TABLE: String =
        "CREATE TABLE ${KontoInfo.TABLE_NAME} (" +
                "${KontoInfo.TABLE_COLUMN_ID} INTEGER PRIMARY KEY," +
                "${KontoInfo.TABLE_COLUMN_KONTO} TEXT NOT NULL,"+
                "${KontoInfo.TABLE_COLUMN_SALDO} DOUBLE NOT NULL)"

    const val SQL_DELETE_TABLE = "DROP TABLE IF EXIST KONTA"
}

object TworzenieTabeliKategori {

    const val SQL_CREATE_TABLE: String =
        "CREATE TABLE ${KategorieInfo.TABLE_NAME} (" +
                "${KategorieInfo.TABLE_COLUMN_ID} INTEGER PRIMARY KEY," +
                "${KategorieInfo.TABLE_COLUMN_KATEGORIA} TEXT NOT NULL)"

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

//    fun dodajWydatek(title: String, category: String, date: String, value: Double, acount: String, note: String, radio: Int){
//        val db: SQLiteDatabase = getWritableDatabase()
//        val row: ContentValues = ContentValues()
//        row.put("Rodzaj", radio)
//        row.put("Notatka", note)
//        row.put("Data", date)
//        row.put("Konto", acount)
//        row.put("Kwota", value)
//        row.put("Tytul", title)
//        row.put("Kategoria", category)
//        db.insertOrThrow("WYDATKI", null, row)
//    }

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

    fun usunWydatek(idWydatku: Int): Boolean{
        val db = this.writableDatabase
        val _success = db.delete(WydatekInfo.TABLE_NAME, WydatekInfo.TABLE_COLUMN_ID + "=?", arrayOf(idWydatku.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun edytujWydatek(idWydatku: Int, nazwaWydatku: String,IdKategori: Int,Data: String,Kwota: Double,IdKonta: Int,Notatka: String,Rodzaj: Int): Boolean{
        val db = this.writableDatabase
        val value = ContentValues()
        value.put(WydatekInfo.TABLE_COLUMN_TYTUL, nazwaWydatku)
        value.put(WydatekInfo.TABLE_COLUMN_IDKAT, IdKategori)
        value.put(WydatekInfo.TABLE_COLUMN_DATA, Data)
        value.put(WydatekInfo.TABLE_COLUMN_KWOTA, Kwota)
        value.put(WydatekInfo.TABLE_COLUMN_IDKONTA, IdKonta)
        value.put(WydatekInfo.TABLE_COLUMN_NOTATKA, Notatka)
        value.put(WydatekInfo.TABLE_COLUMN_RODZAJ, Rodzaj)
        val _success = db.update(WydatekInfo.TABLE_NAME, value, WydatekInfo.TABLE_COLUMN_ID + "=?", arrayOf(idWydatku.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun getIDWydatku(name: String): Contact? {
        val db = this.writableDatabase
        val selectQuery = "SELECT  ID_WYDATKU FROM WYDATKI WHERE Tytul = ?"
        db.rawQuery(selectQuery, arrayOf(name)).use { // .use requires API 16
            if (it.moveToFirst()) {
                val result = Contact()
                result.id = it.getInt(it.getColumnIndex("ID_WYDATKU"))
                return result
            }
        }
        return null
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

    fun usunKonto(idKonta: Int): Boolean{
        val db = this.writableDatabase
        val _success = db.delete(KontoInfo.TABLE_NAME, KontoInfo.TABLE_COLUMN_ID + "=?", arrayOf(idKonta.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun edytujKonto(idKonta: Int, nazwaKonta: String): Boolean{
        val db = this.writableDatabase
        val value = ContentValues()
        value.put(KontoInfo.TABLE_COLUMN_KONTO, nazwaKonta)
        val _success = db.update(KontoInfo.TABLE_NAME, value, KontoInfo.TABLE_COLUMN_ID + "=?", arrayOf(idKonta.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun dodajKategorie(category: String){
        val db: SQLiteDatabase = getWritableDatabase()
        val row: ContentValues = ContentValues()
        row.put("Kategoria", category)
        db.insertOrThrow("KATEGORIE", null, row)
    }

    fun usunKategorie(idKat: Int): Boolean{
        val db = this.writableDatabase
        val _success = db.delete(KategorieInfo.TABLE_NAME, KategorieInfo.TABLE_COLUMN_ID + "=?", arrayOf(idKat.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun edytujKategorie(idKat: Int, nazwaKat: String): Boolean{
        val db = this.writableDatabase
        val value = ContentValues()
        value.put(KategorieInfo.TABLE_COLUMN_KATEGORIA, nazwaKat)
        val _success = db.update(KategorieInfo.TABLE_NAME, value, KategorieInfo.TABLE_COLUMN_ID + "=?", arrayOf(idKat.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
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