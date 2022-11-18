package com.example.chi_9_sqlite.data

data class Book(
    val id: Int,
    val title: String,
    val author: String
) {
    companion object {
        const val TABLE = "Books"
        const val ID = "_id"
        const val TITLE = "title"
        const val AUTHOR = "author"
    }
}
