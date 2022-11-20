package com.example.chi_9_sqlite

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.chi_9_sqlite.DataBaseHelper.Companion.TRIGGER_NAME
import com.example.chi_9_sqlite.data.Book
import com.example.chi_9_sqlite.data.Customer
import com.example.chi_9_sqlite.data.Order
import com.example.chi_9_sqlite.data.OrderBook
import com.example.chi_9_sqlite.data.CustomersBooks

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
                put(Order.CUSTOMER_ID, order.customerId)
            }
            db.insert(Order.TABLE, null, cv)
            cv.clear()
        }
        db.close()
    }

    fun insertCustomers(customers: List<Customer>) {
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

    fun fetchCustomers(version: Int): List<Customer> {
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

                if(version == 1){
                    customers.add(Customer(id, name))
                }else{
                    index = cursor.getColumnIndex(Customer.AGE)
                    val age = cursor.getInt(index)
                    customers.add(Customer(id, name, age))
                }

            } while (cursor.moveToNext())

            cursor.close()
            db.close()
            return customers
        }
        db.close()
        return emptyList()
    }

    //SELECT name, title, author FROM Books b
    // INNER JOIN OrderBook ob ON b._id = ob._id_book
    // INNER JOIN Orders o ON o._id = ob._id_order
    // INNER JOIN Clients c ON o.client = c._id

    fun fetchJoinedData() :List<CustomersBooks>{
        val db = dataBaseHelper.readableDatabase
        val joinQuery = "SELECT ${Customer.NAME}, ${Book.TITLE}, ${Book.AUTHOR} " +
                "FROM ${Book.TABLE} b " +
                "INNER JOIN ${OrderBook.TABLE} ob ON b.${Book.ID} = ob.${OrderBook.BOOK_ID} " +
                "INNER JOIN ${Order.TABLE} o ON o.${Order.ID} = ob.${OrderBook.ORDER_ID} " +
                "INNER JOIN ${Customer.TABLE} c ON o.${Order.CUSTOMER_ID} = c.${Customer.ID}"
        val cursor = db.rawQuery(joinQuery, null)

        if (cursor != null && cursor.count > 0) {
            val customersBooks = ArrayList<CustomersBooks>(cursor.count)
            cursor.moveToFirst()
            do {
               var index = cursor.getColumnIndex(CustomersBooks.NAME)
                val name = cursor.getString(index)

                index = cursor.getColumnIndex(CustomersBooks.TITLE)
                val title = cursor.getString(index)

                index = cursor.getColumnIndex(CustomersBooks.AUTHOR)
                val author = cursor.getString(index)

                customersBooks.add(CustomersBooks(name, title, author))
            } while (cursor.moveToNext())

            cursor.close()
            db.close()
            return customersBooks
        }
        db.close()
        return emptyList()
    }

    //CREATE TRIGGER my_trigger AFTER  INSERT  ON Customers
    //FOR EACH ROW
    //BEGIN
    //UPDATE Customers SET age = 0;
    //END;

    fun createTrigger(){
        val db = dataBaseHelper.writableDatabase
        val createTriggerQuery = "CREATE TRIGGER $TRIGGER_NAME AFTER INSERT ON ${Customer.TABLE} " +
                "FOR EACH ROW BEGIN UPDATE ${Customer.TABLE} set ${Customer.AGE} = 0; END;"
        db?.execSQL(createTriggerQuery)
    }

    fun deleteAll() {
        val db = dataBaseHelper.writableDatabase
        db.delete(Book.TABLE, null, null)
        db.delete(Customer.TABLE, null, null)
        db.delete(Order.TABLE, null, null)
        db.delete(OrderBook.TABLE, null, null)
        db?.execSQL("DROP TRIGGER IF EXISTS $TRIGGER_NAME")
        db.close()
    }
}