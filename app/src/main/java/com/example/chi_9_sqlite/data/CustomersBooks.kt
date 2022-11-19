package com.example.chi_9_sqlite.data

data class CustomersBooks(
    //val customerId :Int,
    val name :String,
    val title :String,
    val author :String
){
    companion object{
        const val CUSTOMER_ID = "_id"
        const val NAME = "name"
        const val TITLE = "title"
        const val AUTHOR = "author"
    }
}
