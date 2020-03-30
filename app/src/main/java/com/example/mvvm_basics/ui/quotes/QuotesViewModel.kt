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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuotesViewModel(private val quoteRepository: QuoteRepository) : ViewModel() {

    // MutableLiveData is from the Architecture Components Library
    // LiveData can be observed for changes
    val quotes = MutableLiveData<MutableList<Quote>>().apply { value = mutableListOf() }
    val authorLD = MutableLiveData<String>().apply { value = "" }
    val quoteLD = MutableLiveData<String>().apply { value = "" }
    val logoSrcCompat = MutableLiveData<Int>().apply { value = R.drawable.ic_favorite_border_black_24dp }
    val bgColor: MutableLiveData<String> = MutableLiveData<String>().apply { value = Color.CYAN.toString() }
    val isVisible: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = true }

    fun getQuotes() = quotes as LiveData<MutableList<Quote>>

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

        println("debug: one - from the thread ${Thread.currentThread().name}")

        val newQuote = Quote(quoteLD.value!!, Student(authorLD.value!!, 21, Hobby("Football", "Soft")))
        val result = withContext(Dispatchers.IO){
            println("debug: two - from the thread ${Thread.currentThread().name}")

            //Add newQuote to Database
            val id = quoteRepository.addQuote(newQuote)
            delay(1000)
            return@withContext quoteRepository.findById(id)
        }
        //Update the ViewModel
        quotes.value!!.add(result)
        logoSrcCompat.value = R.drawable.ic_favorite_border_black_24dp
        isVisible.value = true
        return true
    }

    suspend fun refreshAllDataSuspend() {
        quotes.value = withContext(Dispatchers.IO){
            quoteRepository.getQuotes() as MutableList<Quote>
        }
    }

    fun clearEditText() {
        quoteLD.value = ""
        authorLD.value = ""
    }

    //QuoteDetails Part
    val quoteText = MutableLiveData<String>().apply { value = "" }
    val addCommentsText = MutableLiveData<String>().apply { value = "" }
    var isAlreadyInitialized = false

    suspend fun addDetailQuoteSuspend(): Boolean {
        if(addCommentsText.value == "")
            return false

        quoteText.value = "${quoteText.value}\n${addCommentsText.value}"
        delay(10)
        return true
    }

    fun clearDetailEditText() {
        addCommentsText.value = ""
    }
}