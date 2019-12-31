package com.example.mvvm_basics.ui.quotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_basics.data.Quote
import com.example.mvvm_basics.data.QuoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
             refreshDataSuspend()
         }
    }

    private suspend fun refreshDataSuspend() {
        quoteList = quoteRepository.getQuotes() as MutableList<Quote>
        withContext(Dispatchers.Main){
            quotes.value = quoteList
        }
    }

    fun getQuotes() = quotes as LiveData<List<Quote>>

    suspend fun addQuoteSuspend(quote: Quote) {
        quoteRepository.addQuote(quote)
        refreshDataSuspend()
    }
}