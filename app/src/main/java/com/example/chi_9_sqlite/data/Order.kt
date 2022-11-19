package com.example.chi_9_sqlite.data

data class Order(
    val id: Int,
    val customerId :Int
) {
    companion object {
        const val TABLE = "Orders"
        const val ID = "_id"
        const val CUSTOMER_ID = "client"
    }
}
