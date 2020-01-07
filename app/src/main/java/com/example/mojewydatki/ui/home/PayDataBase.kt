package com.example.mojewydatki.ui.home

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

object PayBase: BaseColumns{

    const val TABLE_NAME = "Wydatek"
    const val payTitle = "Tytul"
    const val payCategory = "Kategoria"
    const val payDate = "Data"
    const val payValue = "Kwota"

}

object TworzenieTabeliWydatek {

    const val SQL_CREATE_TABLE: String =
        "CREATE TABLE ${PayBase.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY" +
                "${PayBase.payTitle} TEXT NOT NULL," +
                "${PayBase.payCategory} TEXT NOT NULL," +
                "${PayBase.payDate} TEXT NOT NULL," +
                "${PayBase.payValue} DOUBLE NOT NULL)"

    const val SQL_DELETE_TABLE = "DROP TABLE IF EXIST ${PayBase.TABLE_NAME}"
}

class PayDataBase(context: Context) : SQLiteOpenHelper(context, PayBase.TABLE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL (TworzenieTabeliWydatek.SQL_CREATE_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL (TworzenieTabeliWydatek.SQL_DELETE_TABLE)
        onCreate(db)
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