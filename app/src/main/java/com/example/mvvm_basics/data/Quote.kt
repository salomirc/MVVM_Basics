package com.example.mvvm_basics.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quote_table")
data class Quote(

    @ColumnInfo(name = "quote_text")
    val quoteText: String,

    @Embedded(prefix = "student_")
    val author: Student)
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "is_selected")
    var isSelected: Boolean = false

    override fun toString(): String {
        return "$id - $quoteText - ${author.Name} - ${author.Age} - ${author.Hobby.Name} - ${author.Hobby.Type}"
    }
}

data class Student(
    val Name: String,
    val Age: Int,
    @Embedded(prefix = "hobby_")
    val Hobby: Hobby
)

data class Hobby(
    val Name: String,
    val Type: String
)