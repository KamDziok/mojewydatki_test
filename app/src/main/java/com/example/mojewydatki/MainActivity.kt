package com.example.mojewydatki

import android.annotation.SuppressLint
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
import com.example.mojewydatki.ui.wydatek.WydatekFragment
import com.example.mojewydatki.ui.home.HomeFragment
import com.example.mojewydatki.ui.kategorie.KategorieFragment
import com.example.mojewydatki.ui.konto.Konto_add_Fragment
import com.example.mojewydatki.ui.podsumowanie.PodsumowanieFragment
import com.example.mojewydatki.ui.przeglad.PrzegladFragment


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    var fm = supportFragmentManager

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fmToFragment = supportFragmentManager

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

    //nawigacja w menu
    fun navHome(){
        val fragment_home = HomeFragment()
        fm.beginTransaction().replace(R.id.nav_host_fragment, fragment_home).commit()
    }

    fun navKategorie(){
        val fragment_kategorie = KategorieFragment()
        fm.beginTransaction().replace(R.id.nav_host_fragment, fragment_kategorie).commit()
    }

    fun navKonto(){
        val fragment_konto = Konto_add_Fragment()
        fm.beginTransaction().replace(R.id.nav_host_fragment, fragment_konto).commit()
    }

    fun navPods(){
        val fragment_pods = PodsumowanieFragment()
        fm.beginTransaction().replace(R.id.nav_host_fragment, fragment_pods).commit()
    }

    fun navPrzeg(){
        val fragment_przeg = PrzegladFragment()
        fm.beginTransaction().replace(R.id.nav_host_fragment, fragment_przeg).commit()
    }

    fun navWydatek(){
        val fragment_wydatek = WydatekFragment()
        //fm.beginTransaction().remove(fm.findFragmentById(R.id.nav_host_fragment)!!.childFragmentManager.fragments.get(0)!!).commit()
        fm.beginTransaction().replace(R.id.nav_host_fragment, fragment_wydatek).commit()
    }
}
