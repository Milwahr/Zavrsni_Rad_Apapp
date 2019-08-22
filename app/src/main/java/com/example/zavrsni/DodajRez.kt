package com.example.zavrsni

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_dodaj_rez.*
import java.util.*

class DodajRez : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dodaj_rez)

        setTitle("Apartments Reservations Application")

        val rezName = intent.getStringExtra("rezName")
        objektIme.text = rezName

        txtnovidatumdol.setOnClickListener(){
            prikDatumDol()
        }
        txtNoviDatumOdl.setOnClickListener(){
            prikDatumOdl()
        }

        spremiRez.setOnClickListener(){
            if(txtnovidatumdol.text.toString().compareTo(txtNoviDatumOdl.text.toString())<0) {
                if (txtnovaRez.text.isEmpty()) {
                    Toast.makeText(this, "Unesite naziv rezervacije", Toast.LENGTH_SHORT).show()
                    txtnovaRez.requestFocus()
                } else if (txtNoviDatumOdl.text.isEmpty()) {
                    Toast.makeText(this, "Unesite datum odlaska", Toast.LENGTH_SHORT).show()
                    txtNoviDatumOdl.requestFocus()
                } else if (txtnovidatumdol.text.isEmpty()) {
                    Toast.makeText(this, "Unesite datum dolaska", Toast.LENGTH_SHORT).show()
                    txtnovidatumdol.requestFocus()
                } else if(txtNoviIznos.text.isEmpty()){
                    Toast.makeText(this, "Unesite iznos", Toast.LENGTH_SHORT).show()
                    txtNoviIznos.requestFocus()
                }
                else if(txtNoviOdrasli.text.isEmpty()){
                    Toast.makeText(this, "Unesite broj odrasli", Toast.LENGTH_SHORT).show()
                    txtNoviIznos.requestFocus()
                }
                else if(txtNoviDjeca.text.isEmpty()){
                    txtNoviDjeca.setText("0")
                }
                    val rezerv = Rezervacije()
                    rezerv.imeRez = txtnovaRez.text.toString()
                    rezerv.datumDOL = txtnovidatumdol.text.toString()
                    rezerv.datumODL = txtNoviDatumOdl.text.toString()
                    rezerv.rezAppNaziv = rezName
                    rezerv.placanje = txtNoviIznos.text.toString() +" "+ noviValuta.text
                    rezerv.odrasli = txtNoviOdrasli.text.toString().toInt()
                    rezerv.djeca = txtNoviDjeca.text.toString().toInt()

                    MainActivity.dbHandler.addRez(this, rezerv)

                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Obavijest!")
                    builder.setMessage("Uspješno dodana nova rezervacija!")
                    builder.setIcon(R.drawable.ic_check_black_24dp)
                    builder.setPositiveButton("Ok") { dialog, which -> }
                    val dialog: AlertDialog = builder.create()
                    dialog.show()

                    clearEdits()
                    txtnovaRez.requestFocus()

            }else{
                upozorenje.setText("Datum odlaska mora biti nakon datuma dolaska")
                upozorenje.setTextColor(Color.RED)
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
        txtNoviDjeca.text.clear()
        txtNoviOdrasli.text.clear()
        txtNoviIznos.text.clear()
        upozorenje.text = ""
    }


    @SuppressLint("SetTextI18n")
    fun prikDatumDol(){
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        val dpd = DatePickerDialog(this, android.R.style.Theme_Holo_Dialog, DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
        txtnovidatumdol.setText("$year/${editMjesec(month)}/${editDan(day)}")}, year, month, day)
        dpd.show()
    }
    @SuppressLint("SetTextI18n")
    fun prikDatumOdl(){
        val c = Calendar.getInstance()
        var day = c.get(Calendar.DAY_OF_MONTH)
        var month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        val dpd = DatePickerDialog(this, android.R.style.Theme_Holo_Dialog, DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            txtNoviDatumOdl.setText("$year/${editMjesec(month)}/${editDan(day)}")}, year, month, day)
        Log.e("TAG", "Normalno")
        dpd.show()
    }
    fun editDan(dan: Int): String{
        var danEdit = dan.toString()
        if(dan<10){
            danEdit = "0$danEdit"
            return danEdit
        }else{
            return dan.toString()
        }
    }
    fun editMjesec(mjesec: Int): String{
        var mjesecEdit = (mjesec+1).toString()
        if(mjesec<10){
            mjesecEdit = "0$mjesecEdit"
            Log.e("MJ", "$mjesecEdit")
            return mjesecEdit
        }else{
            Log.e("MJ", "$mjesec")
            return (mjesec+1).toString()
        }
    }
}
