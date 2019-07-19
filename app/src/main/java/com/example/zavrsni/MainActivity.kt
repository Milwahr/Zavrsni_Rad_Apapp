package com.example.zavrsni

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        lateinit var dbHandler: DBHandler
        lateinit var Iznajmo: Iznajmljivacki
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler = DBHandler(this, null, null, 1)
        prikazObjekti()

        fab.setOnClickListener(){
            val i = Intent(this, addObjekat::class.java)
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
