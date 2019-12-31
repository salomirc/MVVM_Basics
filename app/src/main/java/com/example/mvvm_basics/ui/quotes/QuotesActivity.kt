package com.example.mvvm_basics.ui.quotes

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_basics.R
import com.example.mvvm_basics.data.Quote
import com.example.mvvm_basics.utilities.InjectorUtils
import kotlinx.android.synthetic.main.activity_quotes.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuotesActivity : AppCompatActivity() {

    private var scrollToLastItemRecyclerView: (() -> Unit)? = null

    companion object AppContext {
        lateinit var ApplicationContext: Context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quotes)
        SetApplicationContext()
        initializeUI()
    }

    private fun SetApplicationContext() {
        ApplicationContext = this.applicationContext
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            scrollToLastItemRecyclerView?.invoke()
        }
    }

    private fun initializeUI() {
        // Get the QuotesViewModelFactory with all of it's dependencies constructed
        val factory = InjectorUtils.provideQuotesViewModelFactory()
        // Use ViewModelProviders class to create / get already created QuotesViewModel
        // for this view (activity)
        val viewModel = ViewModelProviders.of(this, factory)
            .get(QuotesViewModel::class.java)

        // Plug in the linear layout manager:
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // Plug in my adapter:
        val adapter = QuoteListAdapter(this)
        recyclerView.adapter = adapter

        viewModel.getQuotes().observe(this, Observer { quotes ->
            // Update the cached copy of the words in the adapter.
            quotes?.let { adapter.setWords(it) }
        })

        // When button is clicked, instantiate a Quote and add it to DB through the ViewModel
        button_add_quote.setOnClickListener {
            viewModel.viewModelScope.launch {
                var quoteTxt = ""
                var authorTxt = ""

                withContext(Dispatchers.Main)
                {
                     quoteTxt = editText_quote.text.toString()
                     authorTxt = editText_author.text.toString()
                }

                val quote = Quote(quoteTxt, authorTxt)
                viewModel.addQuoteSuspend(quote)

                withContext(Dispatchers.Main){
                    scrollToLastItem(viewModel)
                    editText_quote.setText("")
                    editText_author.setText("")
                }
            }
        }

        viewModel.refreshVMData()

        scrollToLastItemRecyclerView = { scrollToLastItem(viewModel) }
    }

    private fun scrollToLastItem(viewModel: QuotesViewModel) {
        recyclerView.scrollToPosition(viewModel.quotes.value!!.size - 1)
    }
}
