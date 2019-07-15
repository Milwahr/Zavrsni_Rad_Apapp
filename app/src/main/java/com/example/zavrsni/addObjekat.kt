package com.example.zavrsni

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_objekat.*

class addObjekat : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_objekat)

        btn_spremi.setOnClickListener(){
            if(edittxt_Naziv.text.isEmpty()){
                Toast.makeText(this, "Unesite ime objekta", Toast.LENGTH_SHORT).show()
                edittxt_Naziv.requestFocus()
            }else{
                val objekat = Iznajmljivacki()
                objekat.nazivApp = edittxt_Naziv.text.toString()

                MainActivity.dbHandler.addObjekti(this, objekat)
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
