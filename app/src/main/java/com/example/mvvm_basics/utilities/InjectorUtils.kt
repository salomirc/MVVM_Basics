package com.example.mvvm_basics.utilities

import com.example.mvvm_basics.data.QuoteRepository
import com.example.mvvm_basics.data.RoomDatabaseApp
import com.example.mvvm_basics.ui.quotes.QuotesActivity
import com.example.mvvm_basics.ui.quotes.QuotesViewModelFactory

object InjectorUtils {

    fun provideQuotesViewModelFactory(): QuotesViewModelFactory {

        val roomDb = RoomDatabaseApp.getInstance(QuotesActivity.ApplicationContext).quoteDao()
        val quoteRepository = QuoteRepository.getInstance(roomDb)
        return QuotesViewModelFactory(quoteRepository)
    }
}