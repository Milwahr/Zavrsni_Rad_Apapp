package com.example.zavrsni

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.editrez.view.*
import kotlinx.android.synthetic.main.novi_prikaz_rez.view.*
import org.w3c.dom.Text

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
        holder.datumRez.text = "${rezervator.datumDOL} - ${rezervator.datumODL}"

        holder.rezDelete.setOnClickListener(){
            val rezervacijaIme = rezervator.imeRez

            var alertDialog = AlertDialog.Builder(context)
                .setTitle("Upozorenje!")
                .setMessage("Jeste li sigurni da biste obrisali rezervaciju $rezervacijaIme?")
                .setPositiveButton("Da", DialogInterface.OnClickListener{dialog, which ->
                    if(MainActivity.dbHandler.deleteRez(rezervator.idRez)){
                        rezerv.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, rezerv.size)
                        Toast.makeText(context, "Obrisana je rezervacija $rezervacijaIme", Toast.LENGTH_SHORT).show()
                    }else
                        Toast.makeText(context, "Greska brisanja $rezervacijaIme", Toast.LENGTH_SHORT).show()
                })
                .setNegativeButton("Ne", DialogInterface.OnClickListener{dialog, which ->})
                .setIcon(R.drawable.ic_warning_black_24dp)
                .show()
        }

        holder.rezEdit.setOnClickListener(){
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.editrez, null)

            val noviRezNaziv : TextView = view.findViewById(R.id.noviRezNaziv)
            val noviDatumDol : TextView = view.findViewById(R.id.noviDatumDol)
            val noviDatumOdl : TextView = view.findViewById(R.id.noviDatumOdl)

            noviRezNaziv.text = rezervator.imeRez
            noviDatumDol.text = rezervator.datumDOL
            noviDatumOdl.text = rezervator.datumODL

            val builder = AlertDialog.Builder(context)
                .setTitle("Uredi Rezervaciju")
                .setView(view)
                .setPositiveButton("Uredi", DialogInterface.OnClickListener{dialog, which ->
                    val isUpdate = MainActivity.dbHandler.editRez(rezervator.idRez.toString(), view.noviRezNaziv.text.toString(),
                        view.noviDatumDol.text.toString(), view.noviDatumOdl.text.toString())
                    if(isUpdate == true){
                        rezerv[position].imeRez = view.noviRezNaziv.text.toString()
                        rezerv[position].datumDOL = view.noviDatumDol.text.toString()
                        rezerv[position].datumODL = view.noviDatumOdl.text.toString()
                        notifyDataSetChanged()
                        Toast.makeText(context, "Uspjesno uredjeno", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context, "Greska u uredjivanju", Toast.LENGTH_SHORT).show()
                    }
                })
                .setNegativeButton("Odustani", DialogInterface.OnClickListener{dialog, which ->})
            val alert = builder.create()
            alert.show()
        }
    }

}