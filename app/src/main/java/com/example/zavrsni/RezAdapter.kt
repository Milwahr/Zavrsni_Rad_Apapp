package com.example.zavrsni

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        holder.datumRez.text = "${rezervator.datumDOL} - ${rezervator.datumODL}"

        /*
        holder.btnDelete.setOnClickListener(){
            val objektIme = iznajmljiv.izn_ime

            var alertDialog = AlertDialog.Builder(mCtx)
                .setTitle("Upozorenje")
                .setMessage("Jeste li sigurni da biste brisali objekat $objektIme?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener{ dialog, which ->
                    if (MainActivity.dbHandler.deleteObjekat(iznajmljiv.izn_id)){
                        iznajApp.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, iznajApp.size)
                        Toast.makeText(mCtx, "Obrisan je objekat $objektIme", Toast.LENGTH_SHORT).show()
                    }else
                        Toast.makeText(mCtx, "Greska u brisanju objekta $objektIme", Toast.LENGTH_SHORT).show()
                })
                .setNegativeButton("No", DialogInterface.OnClickListener{ dialog, which -> })
                .setIcon(R.drawable.ic_warning_black_24dp)
                .show()
        }
         */
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

        /*
        holder.btnUpdate.setOnClickListener(){
            val inflater = LayoutInflater.from(mCtx)
            val view = inflater.inflate(R.layout.objekat_edit, null)

            //val txtObjekatIme : TextView = view.findViewById(R.id.edit_objektIme)
            val txtObjekatIme2 : TextView = view.findViewById(R.id.stari_naziv)

            txtObjekatIme2.text = iznajmljiv.izn_ime

            val builder = AlertDialog.Builder(mCtx)
                .setTitle("Uredi Objekat")
                .setView(view)
                .setPositiveButton("Uredi", DialogInterface.OnClickListener{dialog, which ->
                    val isUpdate = MainActivity.dbHandler.editObjekat(iznajmljiv.izn_id.toString(),
                        view.edit_objektIme.text.toString())
                    if(isUpdate == true){
                        iznajApp[position].izn_ime = view.edit_objektIme.text.toString()
                        notifyDataSetChanged()
                        Toast.makeText(mCtx, "Uspjesno editirano", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(mCtx, "Greska u editiranju", Toast.LENGTH_SHORT).show()
                    }
                })
                .setNegativeButton("Odustani", DialogInterface.OnClickListener{dialog, which ->})
            val alert = builder.create()
            alert.show()
        }
         */
    }

}