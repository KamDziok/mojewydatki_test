package com.example.mojewydatki.ui.home

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mojewydatki.ui.kategorie.Kategoria
import com.example.mojewydatki.ui.konto_.Konto
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

    fun dodajWydatek(title: String, category: Int, date: String, value: Double, acount: Int, note: String, radio: Int){
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
        var konto = getKontoByID(acount)
        edytujSaldoKonta(konto!!.id, (konto!!.saldo + value))
    }

    fun usunWydatek(idWydatku: Int, idKonta: Int, kwota: Double): Boolean{
        val db = this.writableDatabase
        val _success = db.delete(WydatekInfo.TABLE_NAME, WydatekInfo.TABLE_COLUMN_ID + "=?", arrayOf(idWydatku.toString())).toLong()
        val konto = getKontoByID(idKonta)
        edytujSaldoKonta(konto!!.id, konto!!.saldo - kwota)
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

//    fun getWydatekByID(id: Int): Wydatek?{
//        val db = this.writableDatabase
//        val selectQuery = "SELECT ${WydatekInfo.TABLE_COLUMN_ID}, ${WydatekInfo.Ta}, ${KontoInfo.TABLE_COLUMN_SALDO}" +
//                " FROM ${KontoInfo.TABLE_NAME} WHERE ${KontoInfo.TABLE_COLUMN_ID} = ?"
//        db.rawQuery(selectQuery, arrayOf(id.toString())).use {
//            if (it.moveToFirst()) {
//                var result = Konto(it.getInt(it.getColumnIndex(KontoInfo.TABLE_COLUMN_ID)), it.getString(it.getColumnIndex(KontoInfo.TABLE_COLUMN_KONTO)), it.getDouble(it.getColumnIndex(KontoInfo.TABLE_COLUMN_SALDO)))
//                return result
//            }
//        }
//        return null
//    }

    fun getAllWydatki():ArrayList<Wydatek>{
        var list = ArrayList<Wydatek>()
        val db = this.writableDatabase
        val selectQuery = "SELECT  * FROM  ${WydatekInfo.TABLE_NAME} "
        try {
            val cursor = db.rawQuery(selectQuery, null)
            try { // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        var kategorie = getKategoriaById(cursor.getInt(2))
                        var konto = getKontoByID(cursor.getInt(5))
                        val wydatek = Wydatek(cursor.getInt(0), cursor.getString(1), cursor.getString(3),
                            cursor.getDouble(4), cursor.getString(6), cursor.getInt(7))
                        wydatek.setKategoria(kategorie!!)
                        wydatek.setKonto(konto!!)
                        list.add(wydatek)
                    } while (cursor.moveToNext())
                }
            } finally {
                try {
                    cursor.close()
                } catch (ignore: Exception) {
                }
            }
        } finally {
            try {
                db.close()
            } catch (ignore: Exception) {
            }
        }

        return list
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

    fun edytujSaldoKonta(idKonta: Int, saldo: Double): Boolean{
        val db = this.writableDatabase
        val value = ContentValues()
        value.put(KontoInfo.TABLE_COLUMN_SALDO, saldo)
        val _success = db.update(KontoInfo.TABLE_NAME, value, KontoInfo.TABLE_COLUMN_ID + "=?", arrayOf(idKonta.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun getKontoByID(id: Int): Konto?{
        val db = this.writableDatabase
        val selectQuery = "SELECT ${KontoInfo.TABLE_COLUMN_ID}, ${KontoInfo.TABLE_COLUMN_KONTO}, ${KontoInfo.TABLE_COLUMN_SALDO}" +
                " FROM ${KontoInfo.TABLE_NAME} WHERE ${KontoInfo.TABLE_COLUMN_ID} = ?"
        db.rawQuery(selectQuery, arrayOf(id.toString())).use {
            if (it.moveToFirst()) {
                var result = Konto(it.getInt(it.getColumnIndex(KontoInfo.TABLE_COLUMN_ID)), it.getString(it.getColumnIndex(KontoInfo.TABLE_COLUMN_KONTO)), it.getDouble(it.getColumnIndex(KontoInfo.TABLE_COLUMN_SALDO)))
                return result
            }
        }
        return null
    }

    fun getKontaSuma(): Double{
        val db = this.writableDatabase
        val selectQuery = "SELECT SUM(${KontoInfo.TABLE_COLUMN_SALDO})" +
                " FROM ${KontoInfo.TABLE_NAME}"
        db.rawQuery(selectQuery, null).use{
            if (it.moveToFirst()) {
                var result = it.getDouble(it.getColumnIndex("SUM(${KontoInfo.TABLE_COLUMN_SALDO})"))
                return result
            }
        }
        return 0.0
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

    fun getKategoriaById(id: Int): Kategoria?{
        val db = this.writableDatabase
        val selectQuery = "SELECT ${KategorieInfo.TABLE_COLUMN_ID}, ${KategorieInfo.TABLE_COLUMN_KATEGORIA}" +
                " FROM ${KategorieInfo.TABLE_NAME} WHERE ${KategorieInfo.TABLE_COLUMN_ID} = ?"
        db.rawQuery(selectQuery, arrayOf(id.toString())).use {
            if (it.moveToFirst()) {
                var result = Kategoria(it.getInt(it.getColumnIndex(KategorieInfo.TABLE_COLUMN_ID)), it.getString(it.getColumnIndex(KategorieInfo.TABLE_COLUMN_KATEGORIA)))
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

    fun getKategorieAndSuma(): ArrayList<Kategoria>{
        var list = ArrayList<Kategoria>()
        val db = this.writableDatabase
        val selectQuery = "SELECT ${WydatekInfo.TABLE_COLUMN_IDKAT} , SUM(${WydatekInfo.TABLE_COLUMN_KWOTA}) FROM  ${WydatekInfo.TABLE_NAME} " +
                "GROUP BY ${WydatekInfo.TABLE_COLUMN_IDKAT}"
        try {
            val cursor = db.rawQuery(selectQuery, null)
            try { // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        var kategoria = getKategoriaById(cursor.getInt(0))
                        kategoria!!.setSuma(cursor.getDouble(1))
                        list.add(kategoria)
                    } while (cursor.moveToNext())
                }
            } finally {
                try {
                    cursor.close()
                } catch (ignore: Exception) {
                }
            }
        } finally {
            try {
                db.close()
            } catch (ignore: Exception) {
            }
        }

        return list
    }

    fun getAllKat(): ArrayList<Kategoria>{
        var list = ArrayList<Kategoria>()
        val db = this.writableDatabase
        val selectQuery = "SELECT  * FROM  ${KategorieInfo.TABLE_NAME} "
        try {
            val cursor = db.rawQuery(selectQuery, null)
            try { // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        val kategoria = Kategoria(cursor.getInt(0), cursor.getString(1))
                        list.add(kategoria)
                    } while (cursor.moveToNext())
                }
            } finally {
                try {
                    cursor.close()
                } catch (ignore: Exception) {
                }
            }
        } finally {
            try {
                db.close()
            } catch (ignore: Exception) {
            }
        }

        return list
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