package com.example.mvvm_basics.ui.quotes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm_basics.data.QuoteRepository
import kotlinx.coroutines.delay

class QuoteDetailsViewModel(private val quoteRepository: QuoteRepository) : ViewModel() {

    // MutableLiveData is from the Architecture Components Library
    // LiveData can be observed for changes
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