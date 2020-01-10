package com.example.mvvm_basics.data

class QuoteRepository private constructor(private val quoteDao: QuoteDao) {

    suspend fun addQuote(quote: Quote): Long {
        return quoteDao.addQuote(quote)
    }

    suspend fun getQuotes(): List<Quote> {
        return quoteDao.getQuotes()
    }

    companion object {
        @Volatile private var instance: QuoteRepository? = null

        fun getInstance(quoteDao: QuoteDao): QuoteRepository {
            return instance ?: synchronized(this) {
                instance ?: QuoteRepository(quoteDao).also { instance = it }
            }
        }
    }
}