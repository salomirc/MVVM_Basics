package com.example.mvvm_basics

import com.example.mvvm_basics.data.QuoteRepository
import com.example.mvvm_basics.ui.quotes.QuotesViewModel
import io.mockk.clearMocks
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(InstantExecutorExtension::class)
class QuotesViewModelTests {

//    private val quoteDao: QuoteDao = mockk(relaxed = true)
//    private val repo = QuoteRepository.getInstance(quoteDao)
//    private val viewModel by lazy { QuotesViewModel(repo) }

    private val repo = mockk<QuoteRepository>()
    private val viewModel by lazy { QuotesViewModel(repo) }

    @BeforeEach
    fun setUp() {
        clearMocks(repo)
    }

    @Test
    fun add_WhenCalled_ReturnSumOfArguments(){
        val result = viewModel.Add(1, 2)
        assertEquals(3, result)
    }
}