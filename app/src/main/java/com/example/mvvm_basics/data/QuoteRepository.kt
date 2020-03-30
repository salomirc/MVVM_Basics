package com.example.mvvm_basics.data

class QuoteRepository private constructor(private val quoteDao: QuoteDao) {

    fun addQuote(quote: Quote): Long {
        return quoteDao.addQuote(quote)
    }

    fun getQuotes(): List<Quote> {
        return quoteDao.getQuotes()
    }

    fun findById(id: Long): Quote {
        return quoteDao.findById(id)
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