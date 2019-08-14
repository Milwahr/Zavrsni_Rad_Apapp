package com.example.zavrsni

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Adapter
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
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

        setTitle("Iznajmljivački objekti")

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if(id == R.id.action_one){
            val txt1 = "Dobro došli na Appartments Reservations Application"
            val txt2 = "Ova aplikacija služi za organiziranje vaših rezervacija"
            val txt3 = "- Stisnite dolje na plus znak za dodavanje apartmana"
            val txt6 = "- Žuta tipka prikazuje sve rezervacije"
            val txt4 = "- Nakon toga će se prikazati vaši objekti, te stisnite na zelenu strelicu da ih odaberete"
            val txt5 = "- U odabiru će Vam se prikazati apartman te možete dodati rezervacije za taj apartman"
            val txt7 = "Individualna lista prikazuje samo buduće rezervacije, dok u listi 'Sve Rezervacije' prikazuje sve rezervacije"

            val inflater = LayoutInflater.from(this)
            val view = inflater.inflate(R.layout.editrez, null)
            val napomene : TextView = view.findViewById(R.id.napomenetxt)
            val ukratko : TextView = view.findViewById(R.id.kratketxt)
            val upoznavanje : TextView = view.findViewById(R.id.upoznavanjeTxt)

            upoznavanje.setText("$txt1 \n$txt2")
            ukratko.setText("$txt6 \n$txt3 \n$txt4 \n$txt5")
            napomene.setText(txt7)

            val builder = AlertDialog.Builder(this)
                .setTitle("Pomoć")
                .setView(view)
                .setPositiveButton("Uredu", DialogInterface.OnClickListener{dialog, which ->})
                .setIcon(R.drawable.ic_help_outline_blue_24dp)
            val alert = builder.create()
            alert.show()

            Toast.makeText(this, "Pomoć stiže", Toast.LENGTH_LONG).show()
            return true
        }
        return super.onOptionsItemSelected(item)
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
    object Usporedba{
        @RequiresApi(Build.VERSION_CODES.O)
        fun usporedba(datum1: String): Int{
            val cal = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
            val formatted = cal.format(formatter)
            val datum2 = formatted.toString()
            Log.e("TAG", "$datum2 - $datum1")
            if(datum2.compareTo(datum1) > 0) {
                return 0
                Log.e("TAG", "Sad je 0")
            }
            else {
                Log.e("TAG", "Sad je 1")
                return 1
            }
        }
    }
}
