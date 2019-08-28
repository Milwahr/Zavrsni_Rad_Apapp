package com.example.zavrsni

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.editrez.view.*
import kotlinx.android.synthetic.main.novi_prikaz_rez.view.*
import org.w3c.dom.Text
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList

class RezAdapter(context: Context, val rezerv: ArrayList<Rezervacije>): RecyclerView.Adapter<RezAdapter.ViewHolder>() {

    val context = context

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nazivRez = itemView.rezNaziv
        val datumRez = itemView.rezDatum
        val osobaRez = itemView.rezOsoba
        val iznosRez = itemView.rezIznos
        val rezDelete = itemView.deleteRez
        val rezEdit = itemView.editRez
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v2 = LayoutInflater.from(parent.context).inflate(R.layout.novi_prikaz_rez, parent, false)
        return ViewHolder(v2)
    }

    override fun getItemCount(): Int {
        return rezerv.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rezervator: Rezervacije = rezerv[position]
        holder.nazivRez.text = rezervator.imeRez
        holder.datumRez.text = "${rezervator.datumDOL} - ${rezervator.datumODL}"
        holder.iznosRez.text = "${rezervator.placanje}kn"
        holder.osobaRez.text = "${rezervator.odrasli + rezervator.djeca} (Odrasli: ${rezervator.odrasli}, djeca: ${rezervator.djeca})"

        holder.rezDelete.setOnClickListener() {
            val rezervacijaIme = rezervator.imeRez

            var alertDialog = AlertDialog.Builder(context)
                .setTitle("Upozorenje!")
                .setMessage("Da li ste sigurni da želite obrisati rezervaciju $rezervacijaIme?")
                .setPositiveButton("Da", DialogInterface.OnClickListener { dialog, which ->
                    if (MainActivity.dbHandler.deleteRez(rezervator.idRez)) {
                        rezerv.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, rezerv.size)
                        Toast.makeText(context, "Obrisana rezervacija $rezervacijaIme", Toast.LENGTH_SHORT).show()
                    } else
                        Toast.makeText(context, "Greška brisanja $rezervacijaIme", Toast.LENGTH_SHORT).show()
                })
                .setNegativeButton("Ne", DialogInterface.OnClickListener { dialog, which -> })
                .setIcon(R.drawable.ic_warning_black_24dp)
                .show()
        }

        holder.rezEdit.setOnClickListener() {

            val prenosIme = rezervator.imeRez
            val prenosDOL = rezervator.datumDOL
            val prenosODL = rezervator.datumODL
            val prenosID = rezervator.idRez.toString()
            val prenosIznos = rezervator.placanje
            val prenosOdr = rezervator.odrasli.toString()
            val prenosDj = rezervator.djeca.toString()

            val intent = Intent(context, EditRez::class.java)
            intent.putExtra("prenosIme", prenosIme)
            intent.putExtra("prenosDOL", prenosDOL)
            intent.putExtra("prenosODL", prenosODL)
            intent.putExtra("prenosID", prenosID)
            intent.putExtra("prenosIznos", prenosIznos)
            intent.putExtra("prenosOdr", prenosOdr)
            intent.putExtra("prenosDj", prenosDj)
            context.startActivity(intent)
        }
    }
}