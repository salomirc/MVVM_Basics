package com.example.mvvm_basics.utilities

import androidx.lifecycle.ViewModel
import com.example.mvvm_basics.data.QuoteDao
import com.example.mvvm_basics.data.QuoteRepository
import com.example.mvvm_basics.data.RoomDatabaseApp
import com.example.mvvm_basics.ui.quotes.QuoteDetailsViewModel
import com.example.mvvm_basics.ui.quotes.QuotesActivity
import com.example.mvvm_basics.ui.quotes.QuotesViewModel
import com.example.mvvm_basics.ui.quotes.ViewModelFactory

object InjectorUtils {

    private val quoteDao: QuoteDao = RoomDatabaseApp.getInstance(QuotesActivity.ApplicationContext).quoteDao()
    private val quoteRepository: QuoteRepository = QuoteRepository.getInstance(quoteDao)

    fun provideQuotesViewModelFactory(): ViewModelFactory<ViewModel> {
        return ViewModelFactory(QuotesViewModel(quoteRepository))
    }

    fun provideQuotesDetailsViewModelFactory(): ViewModelFactory<ViewModel> {
        return ViewModelFactory(QuoteDetailsViewModel(quoteRepository))
    }
}