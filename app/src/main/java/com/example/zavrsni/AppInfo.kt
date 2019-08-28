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
                "hrvatskim iznajmljivacima za organiziranje rezervacija u iznajmljivackim objektima"
        izradio.text = "Roberto Milic"
        mentor.text = "Doc. dr. sc. Tihomir Orehovacki"
        sveuciliste. text = "Sveuciliste Jurja Dobrile \nFakultet Informatike u Puli"
        razlogIzrade.text = "Aplikacija je izradjena kao zavrsni rad za prediplomski studij"

    }
}
