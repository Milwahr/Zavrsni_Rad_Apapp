package com.example.zavrsni

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DBHandler(context: Context, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, DATABASE_NAME,
    factory, DATABASE_VERSION){

    val context = context
    companion object {


        private val DATABASE_NAME = "apapp.db"
        private val DATABASE_VERSION = 1

        val TABLE_APP_NAME = "Objekti"
        val COLUMN_APP_ID = "appid"
        val COLUMN_APP_NAME = "appime"

        val TABLE_REZ_NAME = "Rezervacije"
        val COLUMN_REZ_ID = "rezid"
        val COLUMN_REZ_NAME = "rezime"
        val COLUMN_REZ_APPNAME = "rezappime"
        val COLUMN_DATE_AR = "datumdol"
        val COLUMN_DATE_LE = "datumodl"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_TABLE_APP = ("CREATE TABLE $TABLE_APP_NAME ($COLUMN_APP_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_APP_NAME TEXT)")
        val CREATE_REZ_APP = ("CREATE TABLE $TABLE_REZ_NAME ($COLUMN_REZ_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_REZ_NAME TEXT, $COLUMN_REZ_APPNAME TEXT, $COLUMN_DATE_AR TEXT" +
                ", $COLUMN_DATE_LE TEXT)")
        p0?.execSQL(CREATE_TABLE_APP)
        p0?.execSQL(CREATE_REZ_APP)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getObjekti(context: Context): ArrayList<Iznajmljivacki>{
        val qry = "SELECT * FROM $TABLE_APP_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(qry, null)
        val objektovi = ArrayList<Iznajmljivacki>()

        if(cursor.count == 0)
            Toast.makeText(context, "Nema objekta", Toast.LENGTH_SHORT).show()
        else{
            cursor.moveToFirst()
            while(!cursor.isAfterLast){
                val objekat = Iznajmljivacki()
                objekat.idApp = cursor.getInt(cursor.getColumnIndex(COLUMN_APP_ID))
                objekat.nazivApp = cursor.getString(cursor.getColumnIndex(COLUMN_APP_NAME))
                objektovi.add(objekat)
                cursor.moveToNext()
            }
            Toast.makeText(context, "Pronadeno ${cursor.count} objekata", Toast.LENGTH_SHORT).show()
        }
        cursor.close()
        db.close()
        return objektovi
    }

    fun getRez(context: Context): ArrayList<Rezervacije>{
        val qry = "SELECT * FROM $TABLE_REZ_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(qry, null)

        val rezerv = ArrayList<Rezervacije>()

        if(cursor.count == 0)
            Toast.makeText(context, "Nema rezervcija", Toast.LENGTH_SHORT).show()
        else{
            cursor.moveToFirst()
            while(!cursor.isAfterLast){
                val rezervac = Rezervacije()
                rezervac.idRez = cursor.getInt(cursor.getColumnIndex(COLUMN_REZ_ID))
                rezervac.imeRez = cursor.getString(cursor.getColumnIndex(COLUMN_REZ_NAME))
                rezervac.rezAppNaziv = cursor.getString(cursor.getColumnIndex(COLUMN_REZ_APPNAME))
                rezervac.datumDOL = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_AR))
                rezervac.datumODL = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_LE))
                rezerv.add(rezervac)
                cursor.moveToNext()
            }
            Toast.makeText(context, "Pronadeno ${cursor.count} rezervacija", Toast.LENGTH_SHORT).show()
        }
        cursor.close()
        db.close()
        return rezerv
    }

    fun getRez2(context: Context, appIme: String): ArrayList<Rezervacije>{
        val qry = "SELECT * FROM $TABLE_REZ_NAME WHERE $COLUMN_REZ_APPNAME = ?"
        val db = this.writableDatabase
        val cursor = db.rawQuery(qry, arrayOf(appIme))

        val rezerv = ArrayList<Rezervacije>()

        if(cursor.count == 0)
            Toast.makeText(context, "Nema rezervacija u objektu $appIme", Toast.LENGTH_SHORT).show()
        else{
            cursor.moveToFirst()
            while (!cursor.isAfterLast){
                val rezervac = Rezervacije()
                rezervac.idRez = cursor.getInt(cursor.getColumnIndex(COLUMN_REZ_ID))
                rezervac.imeRez = cursor.getString(cursor.getColumnIndex(COLUMN_REZ_NAME))
                rezervac.rezAppNaziv = cursor.getString(cursor.getColumnIndex(COLUMN_REZ_APPNAME))
                rezervac.datumDOL = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_AR))
                rezervac.datumODL = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_LE))
                rezerv.add(rezervac)
                cursor.moveToNext()
            }
            Toast.makeText(context, "Pronadjeno ${cursor.count} rezervacija", Toast.LENGTH_SHORT).show()
        }
        cursor.close()
        db.close()
        return rezerv
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getRezSpecific(context: Context, appIme: String): ArrayList<Rezervacije>{
        val qry = "SELECT * FROM $TABLE_REZ_NAME WHERE $COLUMN_REZ_APPNAME = ? ORDER BY $COLUMN_DATE_AR ASC"
        val db = this.writableDatabase
        val cursor = db.rawQuery(qry, arrayOf(appIme))
        val rezerv = ArrayList<Rezervacije>()

        var brojac = 0

        if(cursor.count == 0)
            Toast.makeText(context, "Nema rezervacija u objektu $appIme", Toast.LENGTH_SHORT).show()
        else{
            cursor.moveToFirst()
            while (!cursor.isAfterLast){
                val rezervac = Rezervacije()
                var datumPih = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_LE)).toString()
                if(MainActivity.Usporedba.usporedba(datumPih)==1){
                rezervac.idRez = cursor.getInt(cursor.getColumnIndex(COLUMN_REZ_ID))
                rezervac.imeRez = cursor.getString(cursor.getColumnIndex(COLUMN_REZ_NAME))
                rezervac.rezAppNaziv = cursor.getString(cursor.getColumnIndex(COLUMN_REZ_APPNAME))
                rezervac.datumDOL = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_AR))
                rezervac.datumODL = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_LE))
                rezerv.add(rezervac)
                brojac++
                }else{
                    Log.e("TAG", "Nepripadam")
                }
                cursor.moveToNext()
            }
            Toast.makeText(context, "Pronadjeno $brojac rezervacija", Toast.LENGTH_SHORT).show()
        }
        cursor.close()
        db.close()
        return rezerv
    }

    fun addObjekti(context: Context, objekat: Iznajmljivacki){
        val values = ContentValues()
        values.put(COLUMN_APP_NAME, objekat.nazivApp)

        val db = this.writableDatabase
        try{
            db.insert(TABLE_APP_NAME, null, values)
            Toast.makeText(context, "Uspjesno dodan ${objekat.nazivApp}!", Toast.LENGTH_SHORT).show()
        }catch(e: Exception){
            Toast.makeText(context, "Doslo do greske, jej!", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    fun addRez(context: Context, rezerv: Rezervacije){
        val values = ContentValues()
        values.put(COLUMN_REZ_NAME, rezerv.imeRez)
        values.put(COLUMN_REZ_APPNAME, rezerv.rezAppNaziv)
        values.put(COLUMN_DATE_AR, rezerv.datumDOL)
        values.put(COLUMN_DATE_LE, rezerv.datumODL)

        val db = this.writableDatabase
        try{
            db.insert(TABLE_REZ_NAME, null, values)
            Toast.makeText(context, "Uspjesno dodana rezervacija ${rezerv.imeRez}", Toast.LENGTH_SHORT).show()
        }catch (e: Exception){
            Toast.makeText(context, "Greska u dodavanju rezervacije ${rezerv.imeRez}", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteObjekt(appID : Int, appName: String): Boolean{
        val qry = "DELETE FROM $TABLE_APP_NAME WHERE $COLUMN_APP_ID = $appID"
        val db = this.writableDatabase
        var result : Boolean = false

        try {
            db.execSQL(qry)
            db.delete(TABLE_REZ_NAME, "$COLUMN_REZ_APPNAME = ?", arrayOf(appName))
            result = true
        }catch(e: Exception){
            Log.e(ContentValues.TAG, "Greska u brisanju")
        }
        db.close()
        return result
    }

    fun deleteRez (RezID : Int): Boolean{
        val qry = "DELETE FROM $TABLE_REZ_NAME WHERE $COLUMN_REZ_ID = $RezID"
        val db = this.writableDatabase
        var result : Boolean = false

        try{
            db.execSQL(qry)
            result = true
        }catch (e: Exception){
            Log.e(ContentValues.TAG, "Greska u brisanju")
        }
        db.close()
        return result
    }

    fun editObjekt(id: String, objektIme: String): Boolean{
        var result = false
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_APP_NAME, objektIme)
        try{
            db.update(TABLE_APP_NAME, contentValues, "$COLUMN_APP_ID = ?", arrayOf(id))
            result = true
        }catch (e: Exception){
            Log.e(ContentValues.TAG, "Greska u editiranju")
            result = false
        }
        return result
    }

    fun editRez (id: String, rezIme: String, dateDOL: String, dateODL: String): Boolean{
        var result: Boolean = false
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_REZ_NAME, rezIme)
        contentValues.put(COLUMN_DATE_AR, dateDOL)
        contentValues.put(COLUMN_DATE_LE, dateODL)

        try{
            db.update(TABLE_REZ_NAME, contentValues, "$COLUMN_REZ_ID = ?", arrayOf(id))
            result = true
        }catch (e: Exception){
            Log.e(ContentValues.TAG, "Greska u editiranju")
            result = false
        }
        return result
    }

}