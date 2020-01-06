package com.example.mvvm_basics.utilities

import androidx.lifecycle.ViewModel
import com.example.mvvm_basics.data.QuoteRepository
import com.example.mvvm_basics.data.RoomDatabaseApp
import com.example.mvvm_basics.ui.quotes.QuotesActivity
import com.example.mvvm_basics.ui.quotes.QuotesViewModel
import com.example.mvvm_basics.ui.quotes.QuotesViewModelFactory
import com.example.mvvm_basics.ui.quotes.ViewModelFactory

object InjectorUtils {

//    fun provideQuotesViewModelFactory(): QuotesViewModelFactory {
//
//        val quoteDao = RoomDatabaseApp.getInstance(QuotesActivity.ApplicationContext).quoteDao()
//        val quoteRepository = QuoteRepository.getInstance(quoteDao)
//        return QuotesViewModelFactory(quoteRepository)
//    }

    fun provideQuotesViewModelFactory(): ViewModelFactory<ViewModel> {

        val quoteDao = RoomDatabaseApp.getInstance(QuotesActivity.ApplicationContext).quoteDao()
        val quoteRepository = QuoteRepository.getInstance(quoteDao)
        return ViewModelFactory(QuotesViewModel(quoteRepository))
    }
}