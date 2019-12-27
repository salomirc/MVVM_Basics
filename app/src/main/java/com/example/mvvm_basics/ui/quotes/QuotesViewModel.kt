package com.example.mvvm_basics.ui.quotes

import androidx.lifecycle.ViewModel
import com.example.mvvm_basics.data.Quote
import com.example.mvvm_basics.data.QuoteRepository

class QuotesViewModel(private val quoteRepository: QuoteRepository) : ViewModel() {

    fun getQuotes() = quoteRepository.getQuote()

    fun addQuotes(quote: Quote) = quoteRepository.addQuote(quote)
}