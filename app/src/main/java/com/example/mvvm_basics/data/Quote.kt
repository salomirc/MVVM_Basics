package com.example.mvvm_basics.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quote_table")
data class Quote(

    @ColumnInfo(name = "quote_text")
    val quoteText: String,
    @ColumnInfo(name = "author")
    val author: String)
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    override fun toString(): String {
        return "$id - $quoteText - $author"
    }
}