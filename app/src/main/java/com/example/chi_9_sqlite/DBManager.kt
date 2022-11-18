package com.example.chi_9_sqlite

import android.content.ContentValues
import android.content.Context
import com.example.chi_9_sqlite.data.Film

class DBManager(context: Context) {

    private val dataBaseHelper = DataBaseHelper(context)

    fun insertFilms(list :List<Film>){
        val db = dataBaseHelper.writableDatabase

        val cv = ContentValues()
        list.forEach { film ->
            cv.apply {
                put(Film.ID, film.id)
                put(Film.NAME, film.name)
                put(Film.GENRE, film.genre)
            }
            db.insert(Film.TABLE, null, cv)
            cv.clear()
        }
        //db.close()
    }

}