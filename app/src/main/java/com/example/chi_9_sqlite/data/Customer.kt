package com.example.chi_9_sqlite.data

data class Customer(
    val id: Int,
    val name: String
) {
    companion object {

        const val TABLE = "Customers"
        const val ID = "_id"
        const val NAME = "name"
    }
}
