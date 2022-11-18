package com.example.chi_9_sqlite.data

data class OrderBook(
    val order :Int,
    val book:Int
){
    companion object{
        const val TABLE = "OrderBook"
        const val ORDER_ID = "_id_order"
        const val BOOK_ID = "_id_book"
    }
}
