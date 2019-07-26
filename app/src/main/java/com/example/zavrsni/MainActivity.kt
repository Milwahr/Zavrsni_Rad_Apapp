package com.example.zavrsni

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object{
        lateinit var dbHandler: DBHandler
        lateinit var Iznajmo: Iznajmljivacki
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        val cal = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formatted = cal.format(formatter)
        Toast.makeText(this, "$formatted", Toast.LENGTH_SHORT).show()
        */

        dbHandler = DBHandler(this, null, null, 1)
        prikazObjekti()

        fab.setOnClickListener(){
            val i = Intent(this, addObjekat::class.java)
            startActivity(i)
        }

        fabSve.setOnClickListener(){
            val i = Intent(this, PrikazSvihRez::class.java)
            startActivity(i)
        }
    }

    @SuppressLint("WrongConstant")
    fun prikazObjekti(){
        val objektLista = dbHandler.getObjekti(this)
        viewManager = LinearLayoutManager(this)
        viewAdapter = ObjAdapter(this, objektLista)
        recyclerView = findViewById<RecyclerView>(R.id.rv).apply{
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

    }

    override fun onResume(){
        prikazObjekti()
        super.onResume()
    }
}
