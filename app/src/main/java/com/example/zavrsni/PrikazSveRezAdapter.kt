package com.example.zavrsni

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.novi_prikaz_sve_rez.view.*


class PrikazSveAdapter(context: Context, val rezerv: ArrayList<Rezervacije>): RecyclerView.Adapter<PrikazSveAdapter.ViewHolder>(){

    val context = context
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sveNaziv = itemView.prikazSveRezNaziv
        val sveDatum = itemView.prikazSveDatum
        val sveApp = itemView.prikazSveApp
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
    }

}