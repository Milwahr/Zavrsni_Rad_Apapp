package com.example.zavrsni

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_add_objekat.*

class addObjekat : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_objekat)

        setTitle("Apartments Reservations Application")

        btn_spremi.setOnClickListener(){
            if(edittxt_Naziv.text.isEmpty()){
                Toast.makeText(this, "Unesite ime objekta", Toast.LENGTH_SHORT).show()
                edittxt_Naziv.requestFocus()
            }else{
                val objekat = Iznajmljivacki()
                objekat.nazivApp = edittxt_Naziv.text.toString()

                MainActivity.dbHandler.addObjekti(this, objekat)

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Obavijest!")
                builder.setMessage("Uspješno dodan objekt!")
                builder.setIcon(R.drawable.ic_check_black_24dp)
                builder.setPositiveButton("Ok"){dialog, which ->}
                val dialog: AlertDialog = builder.create()
                dialog.show()

                clearEdits()
                edittxt_Naziv.requestFocus()
            }
        }
        btn_odustani.setOnClickListener(){
            clearEdits()
            finish()
        }
    }

    private fun clearEdits(){
        edittxt_Naziv.text.clear()
    }
}
