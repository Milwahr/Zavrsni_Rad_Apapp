package com.example.zavrsni

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.novi_prikaz_sve_rez.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class PrikazSveAdapter(context: Context, val rezerv: ArrayList<Rezervacije>): RecyclerView.Adapter<PrikazSveAdapter.ViewHolder>(){

    val context = context

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sveNaziv = itemView.prikazSveRezNaziv
        val sveDatum = itemView.prikazSveDatum
        val sveApp = itemView.prikazSveApp
        val statusSve = itemView.statusSve

        val deleteSve = itemView.deletePrikSve
        val infoSve = itemView.infoPrikSve
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrikazSveAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.novi_prikaz_sve_rez, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return rezerv.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: PrikazSveAdapter.ViewHolder, position: Int) {
        val rez : Rezervacije = rezerv[position]
        var status = provjeraStatusa(rez.datumDOL, rez.datumODL)
        holder.sveNaziv.text = rez.imeRez
        holder.sveDatum.text = "${rez.datumDOL} - ${rez.datumODL}"
        holder.sveApp.text = rez.rezAppNaziv
        if (status == 0){
            holder.statusSve.text = "Rezervirano"
            holder.statusSve.setTextColor(Color.GREEN)
        }
        if (status == 1){
            holder.statusSve.text = "Rezervacija u tijeku"
            holder.statusSve.setTextColor(Color.parseColor("#DF7401"))
        }
        if (status == 2){
            holder.statusSve.text = "Prošla rezervacija"
            holder.statusSve.setTextColor(Color.RED)
        }
        if (status == 15){
            holder.statusSve.text = "GREŠKA"
            holder.statusSve.setTextColor(Color.CYAN)
        }

        holder.deleteSve.setOnClickListener(){
            val rezerIme = rez.imeRez
            val rezevApp = rez.rezAppNaziv

            val alertDialog = AlertDialog.Builder(context)
                .setTitle("Upozorenje!")
                .setMessage("Jeste li sigurni da želite obrisati $rezerIme?")
                .setPositiveButton("Da", DialogInterface.OnClickListener{ dialog, which ->
                    if(MainActivity.dbHandler.deleteRez(rez.idRez)){
                        rezerv.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, rezerv.size)
                        Toast.makeText(context, "Obrisana rezervacija $rezerIme s objekta $rezevApp", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(context, "Greška brisanja rezervacije $rezerIme s objekta $rezevApp", Toast.LENGTH_LONG).show()
                    }
                })
                .setNegativeButton("Ne", DialogInterface.OnClickListener{dialog, which ->})
                .setIcon(R.drawable.ic_warning_black_24dp)
                .show()
        }

        holder.infoSve.setOnClickListener(){
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.info_sve, null)
            val infoNaziv : TextView = view.findViewById(R.id.infoNaziv)
            val infoDatum : TextView = view.findViewById(R.id.infoDatum)
            val infoIznos : TextView = view.findViewById(R.id.infoIznos)
            val infoStatus : TextView = view.findViewById(R.id.infoStatus)
            val infoOsoba : TextView = view.findViewById(R.id.infoOsoba)
            val infoObjekt : TextView = view.findViewById(R.id.infoObjekt)

            infoNaziv.setText(holder.sveNaziv.text)
            infoDatum.setText(holder.sveDatum.text)
            infoObjekt.setText(holder.sveApp.text)
            infoStatus.setText(holder.statusSve.text)
            infoIznos.setText("${rez.placanje}kn")
            infoOsoba.setText("${rez.odrasli+rez.djeca} (Odrasli: ${rez.odrasli}, djeca: ${rez.djeca})")

            if (infoStatus.text == "Rezervirano"){
                infoStatus.setTextColor(Color.GREEN)
            }
            else if (infoStatus.text == "Rezervacija u tijeku"){
                infoStatus.setTextColor(Color.parseColor("#DF7401"))
            }
            else{
                infoStatus.setTextColor(Color.RED)
            }

            val builder = AlertDialog.Builder(context)
                .setTitle("Sve informacije")
                .setIcon(R.drawable.ic_info_outline_black_24dp)
                .setView(view)
                .setPositiveButton("Ok", DialogInterface.OnClickListener{dialog, which ->})
            val alert = builder.create()
            alert.show()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun provjeraStatusa(arrival: String, leave: String): Int{
        val cal = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        val formatted = cal.format(formatter)
        val todayDate = formatted.toString()
        if (todayDate.compareTo(arrival)<0 && todayDate.compareTo(leave)<0){
            return 0
        }
        else if (todayDate.compareTo(arrival)>=0 && leave.compareTo(todayDate)>=0){
            return 1
        }
        else if (leave.compareTo(todayDate)<0){
            return 2
        }
        else return 15
    }

}