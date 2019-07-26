package com.example.zavrsni

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PrikazSvihRez : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prikaz_svih_rez)

        setTitle("Sve rezervacije")

        prikazSvihRez()
    }

    fun prikazSvihRez(){
        val sveRezLista = MainActivity.dbHandler.getRez(this)
        viewManager = LinearLayoutManager(this)
        viewAdapter = PrikazSveAdapter(this, sveRezLista)
        recyclerView = findViewById<RecyclerView>(R.id.rv3).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onResume(){
        prikazSvihRez()
        super.onResume()
    }
}
