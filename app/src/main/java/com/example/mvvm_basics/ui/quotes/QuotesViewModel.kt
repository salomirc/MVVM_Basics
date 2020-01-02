package com.example.mvvm_basics.ui.quotes

import androidx.databinding.BaseObservable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_basics.data.Hobby
import com.example.mvvm_basics.data.Quote
import com.example.mvvm_basics.data.QuoteRepository
import com.example.mvvm_basics.data.Student
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuotesViewModel(private val quoteRepository: QuoteRepository) : ViewModel() {

    // MutableLiveData is from the Architecture Components Library
    // LiveData can be observed for changes
    val quotes = MutableLiveData<List<Quote>>().apply { value = mutableListOf() }
    val authorLD = MutableLiveData<String>().apply { value = "" }
    val quoteLD = MutableLiveData<String>().apply { value = "" }

    fun getQuotes() = quotes as LiveData<List<Quote>>

    suspend fun addQuoteSuspend() {
        if(quoteLD.value == "" || authorLD.value == "")
            return
        val quote = Quote(quoteLD.value!!, Student(authorLD.value!!, 21, Hobby("Football", "Soft")))
        quoteRepository.addQuote(quote)
        refreshDataSuspend()
    }

    fun refreshVMData(){
         viewModelScope.launch {
             refreshDataSuspend()
         }
    }

    private suspend fun refreshDataSuspend() {
        quotes.value = quoteRepository.getQuotes() as MutableList<Quote>
    }
}