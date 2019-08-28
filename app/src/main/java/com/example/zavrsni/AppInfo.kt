package com.example.zavrsni

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text

class AppInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_info)
        val oAplikaciji : TextView = findViewById(R.id.oAplikaciji)
        val izradio : TextView = findViewById(R.id.izradio)
        val mentor : TextView = findViewById(R.id.mentor)
        val sveuciliste : TextView = findViewById(R.id.sveuciliste)
        val razlogIzrade : TextView = findViewById(R.id.razlogIzrade)

        oAplikaciji.text = "Apartments Reservations Application (Apapp) je aplikacija namjenjena " +
                "hrvatskim iznajmljivačima za organiziranje rezervacija u iznajmljivačkim objektima"
        izradio.text = "Roberto Milić"
        mentor.text = "Doc. dr. sc. Tihomir Orehovački"
        sveuciliste. text = "Sveučilište Jurja Dobrile \nFakultet Informatike u Puli"
        razlogIzrade.text = "Aplikacija je izrađena kao završni rad za prediplomski studij"

    }
}
