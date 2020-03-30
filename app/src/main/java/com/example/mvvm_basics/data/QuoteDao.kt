package com.example.mvvm_basics.data

import androidx.room.*

@Dao
interface QuoteDao {

    @Query("SELECT * FROM quote_table")
    fun getQuotes(): List<Quote>

    @Query("SELECT * FROM quote_table WHERE id LIKE :id LIMIT 1")
    fun findById(id: Long): Quote

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addQuote(quote: Quote): Long

    @Delete
    fun deleteQuote(quote: Quote)

    @Query("DELETE FROM quote_table")
    fun deleteAllQuotes()
}