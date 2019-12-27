package com.example.mvvm_basics.utilities

import com.example.mvvm_basics.data.FakeDatabase
import com.example.mvvm_basics.data.QuoteRepository
import com.example.mvvm_basics.ui.quotes.QuotesViewModelFactory

object InjectorUtils {

    fun provideQuotesViewModelFactory(): QuotesViewModelFactory {

        val quoteRepository = QuoteRepository.getInstance(FakeDatabase.getInstance().quoteDao)
        return QuotesViewModelFactory(quoteRepository)
    }
}