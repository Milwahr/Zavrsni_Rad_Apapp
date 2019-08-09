package com.example.zavrsni

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.novi_prikaz_sve_rez.view.*


class PrikazSveAdapter(context: Context, val rezerv: ArrayList<Rezervacije>): RecyclerView.Adapter<PrikazSveAdapter.ViewHolder>(){

    val context = context
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sveNaziv = itemView.prikazSveRezNaziv
        val sveDatum = itemView.prikazSveDatum
        val sveApp = itemView.prikazSveApp

        val deleteSve = itemView.deletePrikSve
        val editSve = itemView.editPrikSve
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrikazSveAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.novi_prikaz_sve_rez, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return rezerv.size
    }

    override fun onBindViewHolder(holder: PrikazSveAdapter.ViewHolder, position: Int) {
        val rez : Rezervacije = rezerv[position]
        holder.sveNaziv.text = rez.imeRez
        holder.sveDatum.text = "${rez.datumDOL} - ${rez.datumODL}"
        holder.sveApp.text = rez.rezAppNaziv

        holder.deleteSve.setOnClickListener(){
            val rezerIme = rez.imeRez
            val rezevApp = rez.rezAppNaziv

            val alertDialog = AlertDialog.Builder(context)
                .setTitle("Upozorenje!")
                .setMessage("Jeste li sigurni da zelite obrisati $rezerIme?")
                .setPositiveButton("Da", DialogInterface.OnClickListener{ dialog, which ->
                    if(MainActivity.dbHandler.deleteRez(rez.idRez)){
                        rezerv.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, rezerv.size)
                        Toast.makeText(context, "Obrisana je rezervacija $rezerIme sa objekta $rezevApp", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(context, "Greska brisanja rezervacije $rezerIme sa objekta $rezevApp", Toast.LENGTH_LONG).show()
                    }
                })
                .setNegativeButton("Ne", DialogInterface.OnClickListener{dialog, which ->})
                .setIcon(R.drawable.ic_warning_black_24dp)
                .show()
        }
    }

}