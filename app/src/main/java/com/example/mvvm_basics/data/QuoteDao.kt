package com.example.mvvm_basics.data

import androidx.room.*

@Dao
interface QuoteDao {

    @Query("SELECT * FROM quote_table")
    suspend fun getQuotes(): List<Quote>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addQuote(quote: Quote): Long

    @Delete
    suspend fun deleteQuote(quote: Quote)

    @Query("DELETE FROM quote_table")
    suspend fun deleteAllQuotes()
}