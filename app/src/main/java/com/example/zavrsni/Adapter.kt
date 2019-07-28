package com.example.zavrsni

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.editapp.view.*
import kotlinx.android.synthetic.main.prikaz_objekata.view.*

class ObjAdapter(context: Context, val iznajm: ArrayList<Iznajmljivacki>): RecyclerView.Adapter<ObjAdapter.ViewHolder>(){

    val context = context
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

        holder.btnDelete.setOnClickListener(){
            val objektIme = iznajmljiv.nazivApp

            var alertDialog = AlertDialog.Builder(context)
                .setTitle("Upozorenje!")
                .setMessage("Da li ste sigurno da zelite obrisati $objektIme")
                .setPositiveButton("Da", DialogInterface.OnClickListener{dialog, which ->
                    if(MainActivity.dbHandler.deleteObjekt(iznajmljiv.idApp, iznajmljiv.nazivApp)){
                        iznajm.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, iznajm.size)
                        Toast.makeText(context, "Obrisan je objekt: $objektIme", Toast.LENGTH_SHORT).show()
                    }else
                        Toast.makeText(context, "Greska u brisanju objekta $objektIme", Toast.LENGTH_SHORT).show()
                })
                .setNegativeButton("Ne", DialogInterface.OnClickListener{dialog, which ->})
                .setIcon(R.drawable.ic_warning_black_24dp)
                .show()
        }

        holder.btnEdit.setOnClickListener(){
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.editapp, null)
            val stariNaziv : TextView = view.findViewById(R.id.stariNaziv)

            stariNaziv.text = iznajmljiv.nazivApp

            val builder = AlertDialog.Builder(context)
                .setTitle("Uredi objekt")
                .setView(view)
                .setPositiveButton("Uredi", DialogInterface.OnClickListener{dialog, which ->
                    val isUpdate = MainActivity.dbHandler.editObjekt(iznajmljiv.idApp.toString(),
                        view.noviNaziv.text.toString())
                    if(isUpdate == true){
                        iznajm[position].nazivApp = view.noviNaziv.text.toString()
                        notifyDataSetChanged()
                        Toast.makeText(context, "Uspjesno uredjeno", Toast.LENGTH_SHORT).show()
                    }else
                        Toast.makeText(context, "Greska u uredjivanju", Toast.LENGTH_SHORT).show()
                })
                .setNegativeButton("Odustani", DialogInterface.OnClickListener{dialog, which ->})
            val alert = builder.create()
            alert.show()
        }

        holder.btnSelect.setOnClickListener(){
            val prenosIme = iznajmljiv.nazivApp
            val prenosID = "$iznajmljiv.idApp"

            val intent = Intent(context, PrikazRez::class.java)
            intent.putExtra("rezID", prenosID)
            intent.putExtra("rezIme", prenosIme)
            context.startActivity(intent)
        }
    }

}