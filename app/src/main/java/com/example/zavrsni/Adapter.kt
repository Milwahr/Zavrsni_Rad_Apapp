package com.example.zavrsni

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.prikaz_objekata.view.*

class ObjAdapter(context: Context, val iznajm: ArrayList<Iznajmljivacki>): RecyclerView.Adapter<ObjAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val objIme = itemView.txtNaziv
        val btnSelect = itemView.btn_select
        val btnEdit = itemView.btn_edit
        val btnDelete = itemView.btn_delete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObjAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.prikaz_objekata, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return iznajm.size
    }

    override fun onBindViewHolder(holder: ObjAdapter.ViewHolder, position: Int) {
        val iznajmljiv : Iznajmljivacki = iznajm[position]
        holder.objIme.text = iznajmljiv.nazivApp
    }

}