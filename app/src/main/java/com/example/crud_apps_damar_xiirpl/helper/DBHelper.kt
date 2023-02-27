package com.example.crud_apps_damar_xiirpl.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

class DBHelper (context: Context, factory: CursorFactory?) :
        SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION){

            companion object{
                //deklarasi DB_NAME, DB_VERSION, table
                private val DATABASE_NAME = "crud_profile"
                private val DATABASE_VERSION = 1
                private val TABLE_NAME = "profile"
                val ID_COL = "id"
                val NAME_COL = "name"
                val AGE_COL = "age"
            }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE $TABLE_NAME" +
                     "($ID_COL INTEGER PRIMARY KEY, $NAME_COL TEXT, " +
                     "$AGE_COL TEXT)")

        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db!!.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun addProfile(name: String, age: String){
        val values = ContentValues()

        values.put(NAME_COL, name)
        values.put(AGE_COL, age)

        val db = this.writableDatabase

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getProfile(): Cursor?{
        val db = this.readableDatabase

        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }
}