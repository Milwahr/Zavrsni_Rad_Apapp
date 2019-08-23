package com.example.zavrsni

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.filter_layout.view.*

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu2, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if(id == R.id.filter){
            val inflater = LayoutInflater.from(this)
            val view = inflater.inflate(R.layout.filter_layout, null)
            view.brisiPolja.setOnClickListener(){
                view.filterDol.text.clear()
                view.filterOdl.text.clear()
            }
            val builder = AlertDialog.Builder(this)
                .setTitle("Filter")
                .setView(view)
                .setPositiveButton("Filtriraj", DialogInterface.OnClickListener{dialog, which ->
                    var test = 0
                    var vred: String?
                    val intent = Intent(this, FilterAktivitet::class.java)
                    if(!view.filterDol.text.isEmpty() && !view.filterOdl.text.isEmpty()){
                        test = 1
                    }
                    if(!view.filterObjekt.text.isEmpty() && test == 1){
                        vred = "a"
                        val objekt = view.filterObjekt.text.toString()
                        val datumDol = view.filterDol.text.toString()
                        val datumOdl = view.filterOdl.text.toString()
                        intent.putExtra("objekt", objekt)
                        intent.putExtra("datumOdl", datumOdl)
                        intent.putExtra("datumDol", datumDol)
                        intent.putExtra ("vred", vred)
                        this.startActivity(intent)
                    }
                    else if (view.filterObjekt.text.isEmpty() && test == 1){
                        vred = "b"
                        //val objekt = view.filterObjekt.text.toString()
                        val datumDol = view.filterDol.text.toString()
                        val datumOdl = view.filterOdl.text.toString()
                        //intent.putExtra("objekt", objekt)
                        intent.putExtra("datumOdl", datumOdl)
                        intent.putExtra("datumDol", datumDol)
                        intent.putExtra ("vred", vred)
                        this.startActivity(intent)
                    }
                    else if (!view.filterObjekt.text.isEmpty() && test == 0){
                        vred = "c"
                        val objekt = view.filterObjekt.text.toString()
                        //val datumDol = view.filterDol.text.toString()
                        //val datumOdl = view.filterOdl.text.toString()
                        intent.putExtra("objekt", objekt)
                        //intent.putExtra("datumOdl", datumOdl)
                        //intent.putExtra("datumDol", datumDol)
                        intent.putExtra ("vred", vred)
                        this.startActivity(intent)
                    }else{
                        Toast.makeText(this, "Podaci nisu valjani", Toast.LENGTH_LONG).show()
                    }

                })
            val alert = builder.create()
            alert.show()
            return true
        }
        return super.onOptionsItemSelected(item)
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
