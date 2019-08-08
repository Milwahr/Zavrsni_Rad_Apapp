package com.example.zavrsni

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_prikaz_rez.*
import java.util.*

class PrikazRez : AppCompatActivity() {

    companion object{
        var rezID = 0
        var rezIme = ""
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prikaz_rez)

        val intent = intent

        rezIme = intent.getStringExtra("rezIme")

        setTitle(rezIme)

        prikazRez()

        fab2.setOnClickListener(){
            val intent = Intent(this, DodajRez::class.java)
            intent.putExtra("rezName", rezIme)
            startActivity(intent)
        }

    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun prikazRez(){
        val rezLista = MainActivity.dbHandler.getRezSpecific(this, rezIme)
        viewManager = LinearLayoutManager(this)
        viewAdapter = RezAdapter(this, rezLista)
        recyclerView = findViewById<RecyclerView>(R.id.rv2).apply{
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume(){
        prikazRez()
        super.onResume()
    }

}
