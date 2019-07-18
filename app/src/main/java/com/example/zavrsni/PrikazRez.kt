package com.example.zavrsni

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
        val rezID2 = intent?.getStringExtra("rezId")?.toInt()

        rezIme = intent.getStringExtra("rezIme")

        setTitle(rezIme)
        //Toast.makeText(this, "ID je $rezID2", Toast.LENGTH_SHORT).show()
        prikazRez()

    }
    fun prikazRez(){
        val rezLista = MainActivity.dbHandler.getRez(this)
        viewManager = LinearLayoutManager(this)
        viewAdapter = RezAdapter(this, rezLista)
        recyclerView = findViewById<RecyclerView>(R.id.rv2).apply{
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}
