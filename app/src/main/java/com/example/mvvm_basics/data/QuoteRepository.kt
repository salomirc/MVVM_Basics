package com.example.mvvm_basics.data

class QuoteRepository(private val quoteDao: QuoteDao) {

    fun addQuote(quote: Quote): Long {
        return quoteDao.addQuote(quote)
    }

    fun getQuotes(): List<Quote> {
        return quoteDao.getQuotes()
    }

    fun findById(id: Long): Quote {
        return quoteDao.findById(id)
    }
}