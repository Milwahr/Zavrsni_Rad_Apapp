package com.example.zavrsni

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_dodaj_rez.*

class DodajRez : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dodaj_rez)

        val rezName = intent.getStringExtra("rezName")
        objektIme.text = rezName

        spremiRez.setOnClickListener(){
            if(txtnovaRez.text.isEmpty()){
                Toast.makeText(this, "Unesite naziv rezervacije", Toast.LENGTH_SHORT).show()
                txtnovaRez.requestFocus()
            }
            else if(txtNoviDatumOdl.text.isEmpty()){
                Toast.makeText(this, "Unesite datum odlaska", Toast.LENGTH_SHORT).show()
                txtNoviDatumOdl.requestFocus()
            }
            else if(txtnovidatumdol.text.isEmpty()){
                Toast.makeText(this, "Unesite datum dolaska", Toast.LENGTH_SHORT).show()
                txtnovidatumdol.requestFocus()
            }
            else{
                val rezerv = Rezervacije()
                rezerv.imeRez = txtnovaRez.text.toString()
                rezerv.datumDOL = txtnovidatumdol.text.toString()
                rezerv.datumODL = txtNoviDatumOdl.text.toString()
                rezerv.rezAppNaziv = rezName

                MainActivity.dbHandler.addRez(this, rezerv)
                clearEdits()
                txtnovaRez.requestFocus()
            }
        }

        odustaniRez.setOnClickListener(){
            clearEdits()
            finish()
        }
    }

    private fun clearEdits(){
        txtnovaRez.text.clear()
        txtNoviDatumOdl.text.clear()
        txtnovidatumdol.text.clear()
    }
}
