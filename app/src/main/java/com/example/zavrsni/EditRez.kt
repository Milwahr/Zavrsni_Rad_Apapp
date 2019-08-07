package com.example.zavrsni

import android.app.DatePickerDialog
import android.content.DialogInterface
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

        setTitle("Uredi Rezervaciju")

        editovanNaziv.setText(intent.getStringExtra("prenosIme"))
        editovanDatumDol.setText(intent.getStringExtra("prenosDOL"))
        editovanDatumOdl.setText(intent.getStringExtra("prenosODL"))
        val prenosID = intent.getStringExtra("prenosID")

        editovanDatumDol.setOnClickListener(){
            prikDatumDol()
        }
        editovanDatumOdl.setOnClickListener(){
            prikDatumOdl()
        }

        editSpremuj.setOnClickListener(){
                val builder = AlertDialog.Builder(this)
                    .setTitle("Obavijest")
                    .setIcon(R.drawable.ic_warning_black_24dp)
                    .setMessage("Da li ste sigurni da zelite urediti podatke?")
                    .setPositiveButton("Da", DialogInterface.OnClickListener{dialog, which ->
                        val isUpdate = MainActivity.dbHandler.editRez(prenosID, editovanNaziv.text.toString(), editovanDatumDol.text.toString(),
                            editovanDatumOdl.text.toString())
                        if(isUpdate == true) {
                            Toast.makeText(this, "Uspjesno uredjena rezervacija ${editovanNaziv.text}", Toast.LENGTH_SHORT).show()
                            finish()
                        }else{
                            Toast.makeText(this, "Greska u uredjivanju", Toast.LENGTH_SHORT).show()
                        }
                    })
                    .setNegativeButton("Ne", null)

                    val alert = builder.create()
                    alert.show()
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
            editovanDatumDol.setText("$year/$month/$day")}, year, month, day)
        dpd.show()
    }
    fun prikDatumOdl(){
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        val dpd = DatePickerDialog(this, android.R.style.Theme_Holo_Dialog, DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            editovanDatumOdl.setText("$year/$month/$day")}, year, month, day)
        dpd.show()
    }
}
