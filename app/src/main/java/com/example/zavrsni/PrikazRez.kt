package com.example.zavrsni

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_prikaz_rez.*

class PrikazRez : AppCompatActivity() {

    companion object{
        var rezID = 0
        var rezIme = ""
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

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
    fun prikazRez(){
        val rezLista = MainActivity.dbHandler.getRez2(this, rezIme)
        viewManager = LinearLayoutManager(this)
        viewAdapter = RezAdapter(this, rezLista)
        recyclerView = findViewById<RecyclerView>(R.id.rv2).apply{
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
    override fun onResume(){
        prikazRez()
        super.onResume()
    }
}
