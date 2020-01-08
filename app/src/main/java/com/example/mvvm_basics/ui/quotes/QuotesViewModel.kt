package com.example.mvvm_basics.ui.quotes

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_basics.R
import com.example.mvvm_basics.data.Hobby
import com.example.mvvm_basics.data.Quote
import com.example.mvvm_basics.data.QuoteRepository
import com.example.mvvm_basics.data.Student
import com.example.mvvm_basics.enums.Direction
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuotesViewModel(private val quoteRepository: QuoteRepository) : ViewModel() {

    // MutableLiveData is from the Architecture Components Library
    // LiveData can be observed for changes
    val quotes = MutableLiveData<List<Quote>>().apply { value = mutableListOf() }
    val authorLD = MutableLiveData<String>().apply { value = "" }
    val quoteLD = MutableLiveData<String>().apply { value = "" }
    val logoSrcCompat = MutableLiveData<Int>().apply { value = R.drawable.ic_favorite_border_black_24dp }
    val bgColor: MutableLiveData<String> = MutableLiveData<String>().apply { value = Color.CYAN.toString() }
    val isVisible: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = true }

    fun getQuotes() = quotes as LiveData<List<Quote>>

    fun onLike(direction: Direction){
        viewModelScope.launch {
            quoteLD.value = direction.name
            authorLD.value = quoteLD.value
            addQuoteSuspend()
        }
    }

    suspend fun addQuoteSuspend(): Boolean {
        if(quoteLD.value == "" || authorLD.value == "")
            return false

        logoSrcCompat.value = R.drawable.ic_favorite_black_24dp
        isVisible.value = false
        val quote = Quote(quoteLD.value!!, Student(authorLD.value!!, 21, Hobby("Football", "Soft")))
        quoteRepository.addQuote(quote)
        refreshDataSuspend()
        delay(1000)
        logoSrcCompat.value = R.drawable.ic_favorite_border_black_24dp
        isVisible.value = true
        return true
    }

    fun refreshVMData(){
         viewModelScope.launch {
             refreshDataSuspend()
         }
    }

    private suspend fun refreshDataSuspend() {
        quotes.value = quoteRepository.getQuotes() as MutableList<Quote>
    }

    fun Add(a: Int, b: Int): Int{
        return a + b
    }
}