package com.example.zavrsni

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.novi_prikaz_rez.view.*

class RezAdapter(context: Context, val rezerv: ArrayList<Rezervacije>): RecyclerView.Adapter<RezAdapter.ViewHolder>(){

    val context = context
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nazivRez = itemView.rezNaziv
        val datumRez = itemView.rezDatum
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rezervator : Rezervacije = rezerv[position]
        holder.nazivRez.text = rezervator.imeRez
        var tekstic = "${rezervator.datumDOL} - ${rezervator.datumODL}"
        holder.datumRez.text = tekstic
    }

}