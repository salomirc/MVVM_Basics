package com.example.mvvm_basics.ui.quotes

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm_basics.R
import com.example.mvvm_basics.databinding.ActivityQuotesBinding
import com.example.mvvm_basics.utilities.InjectorUtils
import kotlinx.android.synthetic.main.activity_quotes.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuotesActivity : AppCompatActivity() {

    private lateinit var viewModel: QuotesViewModel
    private var scrollToLastItemRecyclerView: (() -> Unit)? = null

    companion object AppContext {
        lateinit var ApplicationContext: Context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SetApplicationContext()
        SetArchitectureComponents()
        initializeUI()
    }

    private fun SetArchitectureComponents() {
        // Get the QuotesViewModelFactory with all of it's dependencies constructed
        val factory = InjectorUtils.provideQuotesViewModelFactory()
        // Use ViewModelProviders class to create / get already created QuotesViewModel
        // for this view (activity)
        viewModel = ViewModelProviders.of(this, factory)
            .get(QuotesViewModel::class.java)

        // Inflate view and obtain an instance of the binding class.
        val binding: ActivityQuotesBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_quotes)

        // Specify the current activity as the lifecycle owner.
        binding.lifecycleOwner = this

        // Assign the component to a property in the binding class.
        binding.viewmodel = viewModel
    }

    private fun SetApplicationContext() {
        ApplicationContext = this.applicationContext
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ||
            newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            scrollToLastItemRecyclerView?.invoke()
        }
    }

    private fun initializeUI() {

        // Plug in the linear layout manager:
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // Plug in my adapter:
        val adapter = QuoteListAdapter(this)
        recyclerView.adapter = adapter

        //Set Observer for RecyclerView source list
        viewModel.getQuotes().observe(this, Observer { quotes ->
            // Update the cached copy of the words in the adapter.
            quotes?.let { adapter.setWords(it) }
        })

        // When button is clicked, instantiate a Quote and add it to DB through the ViewModel
        button_add_quote.setOnClickListener {
            viewModel.viewModelScope.launch {
                withContext(Dispatchers.Main){
                    hideSoftKeyboard(it)
                }
                delay(500)
                if (viewModel.addQuoteSuspend())
                withContext(Dispatchers.Main){
                    scrollToLastItem(viewModel)
                    clearEditText()
                }
            }
        }

        viewModel.refreshVMData()

        scrollToLastItemRecyclerView = { scrollToLastItem(viewModel) }
    }

    private fun clearEditText() {
        editText_quote.setText("")
        editText_author.setText("")
        editText_quote.clearFocus()
        editText_author.clearFocus()
    }

    private fun hideSoftKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun scrollToLastItem(viewModel: QuotesViewModel) {
        recyclerView.scrollToPosition(viewModel.quotes.value!!.size - 1)
    }
}
