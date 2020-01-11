package com.example.mvvm_basics

import com.example.mvvm_basics.data.Hobby
import com.example.mvvm_basics.data.Quote
import com.example.mvvm_basics.data.QuoteRepository
import com.example.mvvm_basics.data.Student
import com.example.mvvm_basics.ui.quotes.QuotesViewModel
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(InstantExecutorExtension::class)
class QuotesViewModelTests {

    private val repo = mockk<QuoteRepository>()
    private val viewModel by lazy { QuotesViewModel(repo) }

    private val sampleList = mutableListOf(
        Quote("abc", Student("Ciprian", 21, Hobby("Footbal", "Sport"))),
        Quote("abc", Student("Viorel", 22, Hobby("Basket", "Sport")))
    )

    @BeforeEach
    fun setUp() {
        clearMocks(repo)
    }

    @Test
    fun getQuotes_WhenCalled_ReturnsLiveDataofListofQuotes(){
        // Arrange
        viewModel.quotes.value = sampleList

        // Act
        val result = viewModel.getQuotes().value

        // Assert
        assertThat(result).isEqualTo(sampleList)
    }

    @Test
    fun addQuoteSuspend_quoteLD_is_EmptyString_ReturnsFalse(){
        // Arrange
        viewModel.quoteLD.value = ""
        viewModel.authorLD.value = "AAA"

        // Act
        val result = runBlocking { viewModel.addQuoteSuspend() }

        // Assert
        assertThat(result).isEqualTo(false)
    }

    @Test
    fun addQuoteSuspend_authorLD_is_EmptyString_ReturnsFalse(){
        // Arrange
        viewModel.quoteLD.value = "BBB"
        viewModel.authorLD.value = ""

        // Act
        val result = runBlocking { viewModel.addQuoteSuspend() }

        // Assert
        assertThat(result).isEqualTo(false)
    }

    @Test
    fun addQuoteSuspend_CorrectInput_ReturnsTrue(){
        // Arrange
        viewModel.quoteLD.value = "AAA"
        viewModel.authorLD.value = "Ciprian"
        coEvery { repo.addQuote(any()) } returns 0

        // Act
        val result = runBlocking { viewModel.addQuoteSuspend() }

        // Assert
        coVerifySequence {
            repo.addQuote(any())
        }
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun refreshDataSuspend_WhenCalled_setCorrectState(){
        // Arrange
//        val quote = Quote("abc", Student("Ciprian", 21, Hobby("Footbal", "Sport")))
        coEvery { repo.getQuotes() } returns sampleList

        // Act
        runBlocking { viewModel.refreshAllDataSuspend() }

        // Assert
//        val newSampleList = mutableListOf<Quote>().apply { addAll(sampleList); add(quote) }
        coVerify { repo.getQuotes() }
        assertThat(viewModel.quotes.value).isEqualTo(sampleList)
     }
}