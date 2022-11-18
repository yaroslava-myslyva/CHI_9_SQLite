package com.example.chi_9_sqlite.data

data class Order(
    val id: Int,
    val clientId :Int
) {
    companion object {
        const val TABLE = "Orders"
        const val ID = "_id"
        const val CLIENT_ID = "client"
    }
}
