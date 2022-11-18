package com.example.chi_9_sqlite.data

data class Client(
    val id: Int,
    val name: String
) {
    companion object {

        const val TABLE = "Clients"
        const val ID = "_id"
        const val NAME = "name"
    }
}
