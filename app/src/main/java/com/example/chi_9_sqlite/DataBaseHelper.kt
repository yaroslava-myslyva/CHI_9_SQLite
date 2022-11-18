package com.example.chi_9_sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.chi_9_sqlite.data.Book
import com.example.chi_9_sqlite.data.Client
import com.example.chi_9_sqlite.data.Order
import com.example.chi_9_sqlite.data.OrderBook

class DataBaseHelper(context: Context) : SQLiteOpenHelper(
    context, DB_NAME, null, DB_VERSION
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createBooksSql = "CREATE TABLE ${Book.TABLE} (" +
                Book.ID + " INTEGER PRIMARY KEY, " +
                Book.TITLE + " TEXT NOT NULL, " +
                Book.AUTHOR + " TEXT NOT NULL);"
        db?.execSQL(createBooksSql)

        val createClientsSql = "CREATE TABLE ${Client.TABLE} (" +
                Client.ID + " INTEGER PRIMARY KEY, " +
                Client.NAME + " TEXT NOT NULL);"
        db?.execSQL(createClientsSql)

        val createOrdersSql = "CREATE TABLE ${Order.TABLE} (" +
                Order.ID + " INTEGER PRIMARY KEY, " +
                Order.CLIENT_ID + " INTEGER NOT NULL, FOREIGN KEY (${Order.CLIENT_ID}) REFERENCES ${Client.TABLE} (${Client.ID}) ON DELETE CASCADE ON UPDATE CASCADE);"
        db?.execSQL(createOrdersSql)
        Log.d("ttt", createOrdersSql)

        val createOrderBookSql = "CREATE TABLE ${OrderBook.TABLE} (" +
                OrderBook.ORDER_ID + " INTEGER NOT NULL, " +
                OrderBook.BOOK_ID + " INTEGER NOT NULL, " +
                "FOREIGN KEY (${OrderBook.ORDER_ID}) REFERENCES ${Order.TABLE} (${Order.ID}) ON DELETE CASCADE ON UPDATE CASCADE, " +
                "FOREIGN KEY (${OrderBook.BOOK_ID}) REFERENCES ${Book.TABLE} (${Book.ID}) ON DELETE CASCADE ON UPDATE CASCADE);"
        db?.execSQL(createOrderBookSql)


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${Client.TABLE};")
        db?.execSQL("DROP TABLE IF EXISTS ${Order.TABLE};")
        db?.execSQL("DROP TABLE IF EXISTS ${Book.TABLE};")
        db?.execSQL("DROP TABLE IF EXISTS ${OrderBook.TABLE};")
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "myDataBase"
        private const val DB_VERSION = 1
    }
}