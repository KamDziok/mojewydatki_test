package com.example.mojewydatki.ui.wydatek

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mojewydatki.R
import kotlinx.android.synthetic.main.fragment_wydatek.*
import java.util.*
import android.content.Context
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.app_bar_main.*


class WydatekFragment : Fragment() {

    private lateinit var wydatekViewModel: WydatekViewModel

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        wydatekViewModel =
                ViewModelProviders.of(this).get(WydatekViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_wydatek, container, false)

        //Obsluga FloatingActionButton (tego plusa) ukrycie
        val fab = activity!!.fab as? FloatingActionButton
        fab!!.visibility = View.INVISIBLE

        val payData = root.findViewById<View>(R.id.payDate_textedit)
        payData.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val activity = activity as Context
            val dpd = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener{ view, mYear, mMonth, mDay -> payDate_textedit.setText(""+mDay+"."+(mMonth+1)+"."+mYear)}, year,month,day)
            dpd.show()
        }
       // val textView: TextView = root.findViewById(R.id.text_wydatek)
      //  wydatekViewModel.text.observe(this, Observer {
       //     textView.text = it
      //  })
        return root
    }
}