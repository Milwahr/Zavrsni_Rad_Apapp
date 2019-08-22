package com.example.zavrsni

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_edit_rez.*
import java.util.*

class EditRez : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_rez)

        intent = intent

        setTitle("Apartment Reservation Application")

        editovanNaziv.setText(intent.getStringExtra("prenosIme"))
        editovanDatumDol.setText(intent.getStringExtra("prenosDOL"))
        editovanDatumOdl.setText(intent.getStringExtra("prenosODL"))
        editovanIznos.setText(intent.getStringExtra("prenosIznos"))
        editovanOdrasli.setText(intent.getStringExtra("prenosOdr"))
        editovanDjeca.setText(intent.getStringExtra("prenosDj"))
        val prenosID = intent.getStringExtra("prenosID")

        editovanDatumDol.setOnClickListener(){
            prikDatumDol()
        }
        editovanDatumOdl.setOnClickListener(){
            prikDatumOdl()
        }

        editSpremuj.setOnClickListener(){
            if(editovanDatumDol.text.toString().compareTo(editovanDatumOdl.text.toString())<0) {
                val builder = AlertDialog.Builder(this)
                    .setTitle("Obavijest")
                    .setIcon(R.drawable.ic_warning_black_24dp)
                    .setMessage("Da li ste sigurni da želite urediti podatke?")
                    .setPositiveButton("Da", DialogInterface.OnClickListener { dialog, which ->
                        val isUpdate = MainActivity.dbHandler.editRez(
                            prenosID, editovanNaziv.text.toString(), editovanDatumDol.text.toString(),
                            editovanDatumOdl.text.toString(), editovanOdrasli.text.toString().toInt(),
                            editovanDjeca.text.toString().toInt(), editovanIznos.text.toString()
                        )
                        if(editovanNaziv.text.isEmpty()){
                            Toast.makeText(this, "Unesite naziv rezervacije", Toast.LENGTH_SHORT).show()
                            editovanNaziv.requestFocus()
                        }
                        else if (editovanDatumDol.text.isEmpty()){
                            Toast.makeText(this, "Unesite datum dolaska", Toast.LENGTH_SHORT).show()
                            editovanDatumDol.requestFocus()
                        }
                        else if (editovanDatumOdl.text.isEmpty()){
                            Toast.makeText(this, "Unesite datum odlaska", Toast.LENGTH_SHORT).show()
                            editovanDatumOdl.requestFocus()
                        }
                        else if (editovanIznos.text.isEmpty()){
                            Toast.makeText(this, "Unesite iznos", Toast.LENGTH_SHORT).show()
                            editovanIznos.requestFocus()
                        }
                        else if (editovanOdrasli.text.isEmpty()){
                            Toast.makeText(this, "Unesite broj odrasli", Toast.LENGTH_SHORT).show()
                            editovanOdrasli.requestFocus()
                        }
                        else if (editovanDjeca.text.isEmpty()){
                            Toast.makeText(this, "Unesite broj djece", Toast.LENGTH_SHORT).show()
                            editovanDjeca.requestFocus()
                        }
                        else{
                        if (isUpdate == true) {
                            Toast.makeText(
                                this,
                                "Ušpjesno uređena rezervacija ${editovanNaziv.text}",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(this, "Greška u uređivanju", Toast.LENGTH_SHORT).show()
                        }
                    }
                    })
                    .setNegativeButton("Ne", null)

                val alert = builder.create()
                alert.show()
                upozorenjeEdit.setText("")
            }else{
                upozorenjeEdit.setText("Datum odlaska mora biti nakon datuma dolaska")
                upozorenjeEdit.setTextColor(Color.RED)
            }
        }

        editOdustanuj.setOnClickListener(){
            finish()

        }
    }

    fun prikDatumDol(){
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        val dpd = DatePickerDialog(this, android.R.style.Theme_Holo_Dialog, DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            editovanDatumDol.setText("$year/${editMjesec(month)}/${editDan(day)}")}, year, month, day)
        dpd.show()
    }
    fun prikDatumOdl(){
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        val dpd = DatePickerDialog(this, android.R.style.Theme_Holo_Dialog, DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            editovanDatumOdl.setText("$year/${editMjesec(month)}/${editDan(day)}")}, year, month, day)
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
            return mjesecEdit
        }else{
            return (mjesec+1).toString()
        }
    }
}
