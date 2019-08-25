package com.example.zavrsni

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FilterAktivitet : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_aktivitet)

        setTitle("Apartments Reservation Application")
        intent = intent

        val vred = intent.getStringExtra("vred")
        var godina: String = ""
        var objekt: String = ""
        if(vred == "a"){
            godina = intent.getStringExtra("godina")
            objekt = intent.getStringExtra("objekt")
        }
        else if(vred == "b"){
            godina = intent.getStringExtra("godina")
        }
        else if(vred == "c"){
            objekt = intent.getStringExtra("objekt")
        }else{
            val intent2 = Intent(this, MainActivity::class.java)
            Toast.makeText(this, "Greska", Toast.LENGTH_LONG).show()
            this.startActivity(intent2)
        }
        prikazFilterRez(vred, objekt, godina)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun prikazFilterRez(vred: String, objekt: String, godina: String){
        val sveRezLista = MainActivity.dbHandler.filterRez(this, vred, objekt, godina)
        viewManager = LinearLayoutManager(this)
        viewAdapter = PrikazSveAdapter(this, sveRezLista)
        recyclerView = findViewById<RecyclerView>(R.id.filter_rv).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

}
