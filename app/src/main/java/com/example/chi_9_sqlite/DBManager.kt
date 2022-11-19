package com.example.chi_9_sqlite

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.chi_9_sqlite.data.Book
import com.example.chi_9_sqlite.data.Customer
import com.example.chi_9_sqlite.data.Order
import com.example.chi_9_sqlite.data.OrderBook

class DBManager(context: Context) {

    val dataBaseHelper = DataBaseHelper(context)

    fun insertBooks(books: List<Book>) {
        val db = dataBaseHelper.writableDatabase
        val cv = ContentValues()
        books.forEach { book ->
            cv.apply {
                put(Book.ID, book.id)
                put(Book.TITLE, book.title)
                put(Book.AUTHOR, book.author)
            }
            db.insert(Book.TABLE, null, cv)
            cv.clear()
        }
        db.close()
    }

    fun insertOrders(orders: List<Order>) {
        val db = dataBaseHelper.writableDatabase
        val cv = ContentValues()
        orders.forEach { order ->
            cv.apply {
                put(Order.ID, order.id)
                put(Order.CLIENT_ID, order.clientId)
            }
            db.insert(Order.TABLE, null, cv)
            cv.clear()
        }
        db.close()
    }

    fun insertClients(customers: List<Customer>) {
        val db = dataBaseHelper.writableDatabase
        val cv = ContentValues()
        customers.forEach { client ->
            cv.apply {
                put(Customer.ID, client.id)
                put(Customer.NAME, client.name)
            }
            db.insert(Customer.TABLE, null, cv)
            cv.clear()
        }
        db.close()
    }

    fun insertOrderBooks(orderBooks :List<OrderBook>){
        val db = dataBaseHelper.writableDatabase
        val cv = ContentValues()
        orderBooks.forEach { orderBook ->
            cv.apply {
                put(OrderBook.ORDER_ID, orderBook.order)
                put(OrderBook.BOOK_ID, orderBook.book)
            }
            db.insert(OrderBook.TABLE, null, cv)
            cv.clear()
        }
        db.close()
    }

    fun fetchFilms(): List<Customer> {
        val db = dataBaseHelper.readableDatabase
        val cursor = db.query(Customer.TABLE, null, null, null, null, null, null)

        if (cursor != null && cursor.count > 0) {
            val customers = ArrayList<Customer>(cursor.count)
            cursor.moveToFirst()
            do {
                var index = cursor.getColumnIndex(Customer.ID)
                val id = cursor.getInt(index)

                index = cursor.getColumnIndex(Customer.NAME)
                val name = cursor.getString(index)
                customers.add(Customer(id, name))
            } while (cursor.moveToNext())

            cursor.close()
            db.close()
            return customers
        }
        db.close()
        return emptyList()
    }

    fun deleteAll() {
        val db = dataBaseHelper.writableDatabase
        val a = db.delete(Book.TABLE, null, null)
        Log.d("ttt", "deleteAll - $a")
        db.delete(Customer.TABLE, null, null)
        db.delete(Order.TABLE, null, null)
        db.delete(OrderBook.TABLE, null, null)
        db.close()
    }
}