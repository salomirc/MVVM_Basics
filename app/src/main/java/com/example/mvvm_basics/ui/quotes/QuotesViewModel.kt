package com.example.mvvm_basics.ui.quotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_basics.data.Quote
import com.example.mvvm_basics.data.QuoteRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuotesViewModel(private val quoteRepository: QuoteRepository) : ViewModel() {

    var quoteList = mutableListOf<Quote>()
    // MutableLiveData is from the Architecture Components Library
    // LiveData can be observed for changes
    val quotes = MutableLiveData<List<Quote>>()

    init {
        // Immediately connect the now empty quoteList
        // to the MutableLiveData which can be observed
        quotes.value = quoteList
    }

    fun refreshVMData(){
         viewModelScope.launch {
            quoteList = quoteRepository.getQuotes() as MutableList<Quote>
            quotes.value = quoteList
        }
    }

    fun getQuotes() = quotes as LiveData<List<Quote>>

    fun addQuote(quote: Quote) {
        viewModelScope.launch {
            quoteRepository.addQuote(quote)
            // After adding a quote to the "database",
            // update the value of MutableLiveData
            // which will notify its active observers
            quoteList.add(quote)
            quotes.value = quoteList
        }
    }
}