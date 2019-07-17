package com.example.zavrsni

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

class DBHandler(context: Context, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, DATABASE_NAME,
    factory, DATABASE_VERSION){

    val context = context
    companion object{
        private val DATABASE_NAME = "apapp.db"
        private val DATABASE_VERSION = 1

        val TABLE_APP_NAME = "Objekti"
        val COLUMN_APP_ID = "appid"
        val COLUMN_APP_NAME = "appime"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_TABLE_APP = ("CREATE TABLE $TABLE_APP_NAME ($COLUMN_APP_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_APP_NAME TEXT)")
        p0?.execSQL(CREATE_TABLE_APP)
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

    fun deleteObjekt(appID : Int): Boolean{
        val qry = "DELETE FROM $TABLE_APP_NAME WHERE $COLUMN_APP_ID = $appID"
        val db = this.writableDatabase
        var result : Boolean = false

        try {
            val cursor = db.execSQL(qry)
            result = true
        }catch(e: Exception){
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

}