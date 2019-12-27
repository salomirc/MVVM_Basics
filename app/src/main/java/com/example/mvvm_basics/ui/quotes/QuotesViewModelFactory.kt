package com.example.mvvm_basics.ui.quotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_basics.data.QuoteRepository

class QuotesViewModelFactory(private val quoteRepository: QuoteRepository) : ViewModelProvider.NewInstanceFactory() {

//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return super.create(modelClass)
//    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return QuotesViewModel(quoteRepository) as T
    }
}