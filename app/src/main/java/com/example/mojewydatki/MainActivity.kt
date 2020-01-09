package com.example.mojewydatki

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mojewydatki.ui.home.HomeAdapter
import com.example.mojewydatki.ui.home.PayDataBase
import com.example.mojewydatki.ui.wydatek.WydatekFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_wydatek.*
import java.util.*
import java.text.NumberFormat
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        dodaj_wydatek_button.setOnClickListener{    //listener przycisku dodawania wydatku
            val title: String = categoryTitle_textedit.getText().toString()
            val saldo  = konto_saldop_textedit.getText().toString()
            val category = payCategory_textedit.getText().toString()
            val day = payDate_textedit.getText().toString()
            val acount = payKonto_textedit.getText().toString()
            val note = payNotatka_textedit.getText().toString()
            var radio = -1
            if(radioButton_wplyw.isChecked()){
                radio = 0
            }
            if(radioButton_wydatek.isChecked()){
                radio = 1
            }

            if(title.isNotEmpty() && saldo.isNotEmpty() && category.isNotEmpty() && day.isNotEmpty() && acount.isNotEmpty() && note.isNotEmpty() && radio >= 0){
                try {
                    val db: PayDataBase = PayDataBase(this)
                    val nf = NumberFormat.getInstance()
                    val saldo = nf.parse(saldo).toDouble()
                    db.dodajWydatek(title, category, day, saldo, acount, note, radio)
                }catch (e: Exception) {
                    var mesage = Toast.makeText(applicationContext, "Coś poszło nie tak", Toast.LENGTH_SHORT)
                    mesage.show()
                }
            }else{
                var mesage = Toast.makeText(applicationContext, "Podaj wszystkie dane", Toast.LENGTH_SHORT)
                mesage.show()
            }
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //Ukrycie FloatingActionButton (ten plus)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.visibility = View.INVISIBLE

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home, R.id.nav_przeglad, R.id.nav_wydatek,
            R.id.nav_konto, R.id.nav_kategorie, R.id.nav_podsumowanie), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
