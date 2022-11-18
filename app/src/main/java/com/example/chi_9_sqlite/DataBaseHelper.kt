package com.example.chi_9_sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.chi_9_sqlite.data.Film

class DataBaseHelper(context: Context) : SQLiteOpenHelper(
    context, DB_NAME, null, DB_VERSION
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createFilmSql = "CREATE TABLE ${Film.TABLE} (" +
                Film.ID + " INTEGER PRIMARY KEY, " +
                Film.NAME + " TEXT NOT NULL, " +
                Film.GENRE + " TEXT NOT NULL);"
        db?.execSQL(createFilmSql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + Film.TABLE)
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "myDataBase"
        private const val DB_VERSION = 1
    }
}