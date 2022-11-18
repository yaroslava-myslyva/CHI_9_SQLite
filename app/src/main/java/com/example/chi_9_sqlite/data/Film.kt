package com.example.chi_9_sqlite.data

data class Film(
    val id:Int,
    val name :String,
    val genre :String
){
    companion object{

        const val TABLE = "Films"
        const val ID = "_id"
        const val NAME = "name"
        const val GENRE = "genre"

        const val DESCRIPTION = "description"
    }
}
