package com.example.chi_9_sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.chi_9_sqlite.data.Book
import com.example.chi_9_sqlite.data.Customer
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

        val createClientsSql = "CREATE TABLE ${Customer.TABLE} (" +
                Customer.ID + " INTEGER PRIMARY KEY, " +
                Customer.NAME + " TEXT NOT NULL);"
        db?.execSQL(createClientsSql)

        val createOrdersSql = "CREATE TABLE ${Order.TABLE} (" +
                Order.ID + " INTEGER PRIMARY KEY, " +
                Order.CUSTOMER_ID + " INTEGER NOT NULL, FOREIGN KEY (${Order.CUSTOMER_ID}) REFERENCES ${Customer.TABLE} (${Customer.ID}) ON DELETE CASCADE ON UPDATE CASCADE);"
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
        when(newVersion){
            DB_VERSION -> {
                db?.execSQL("DROP TABLE IF EXISTS ${Customer.TABLE};")
                db?.execSQL("DROP TABLE IF EXISTS ${Order.TABLE};")
                db?.execSQL("DROP TABLE IF EXISTS ${Book.TABLE};")
                db?.execSQL("DROP TABLE IF EXISTS ${OrderBook.TABLE};")
                db?.execSQL("DROP TRIGGER IF EXISTS $TRIGGER_NAME")
                onCreate(db)
            }
            DB_VERSION_RELEASE_1_1 -> {
                migrate(db)
            }
        }
    }

    private fun migrate(db: SQLiteDatabase?) {
        val alterCustomerSql = "ALTER TABLE ${Customer.TABLE} " +
                "ADD ${Customer.AGE} INTEGER"
        db?.execSQL(alterCustomerSql)
    }

    companion object {
        private const val DB_NAME = "myDataBase"
        private const val DB_VERSION = 1
        private const val DB_VERSION_RELEASE_1_1 = 2
        const val TRIGGER_NAME = "my_trigger"
    }
}