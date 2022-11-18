package com.example.chi_9_sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chi_9_sqlite.data.Film

class MainActivity : AppCompatActivity() {
    private var dbManager :DBManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbManager = DBManager(this)
        dbManager?.insertFilms(
            listOf(
                Film(1, "ti", "tu")
            )
        )
    }
}