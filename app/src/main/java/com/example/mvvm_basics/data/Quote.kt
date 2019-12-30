package com.example.mvvm_basics.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quote_table")
data class Quote(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "quote_text")
    val quoteText: String,
    @ColumnInfo(name = "author")
    val author: String)
{

    override fun toString(): String {
        return "$id - $quoteText - $author"
    }
}