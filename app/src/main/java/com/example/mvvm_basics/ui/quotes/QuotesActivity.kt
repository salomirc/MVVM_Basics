package com.example.mvvm_basics.ui.quotes

import android.content.Context
import android.content.res.Configuration
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm_basics.R
import com.example.mvvm_basics.databinding.ActivityQuotesBinding
import com.example.mvvm_basics.utilities.InjectorUtils
import kotlinx.android.synthetic.main.activity_quotes.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuotesActivity : AppCompatActivity() {

    private lateinit var viewModel: QuotesViewModel
    private var scrollToLastItemRecyclerView: (() -> Unit)? = null
    private var isRunning: Boolean = false

    companion object AppContext {
        lateinit var ApplicationContext: Context
        lateinit var rootView: ViewGroup

        fun isKeyboardOnScreen(): Boolean{
            val r = Rect()
            rootView.getWindowVisibleDisplayFrame(r)
            val heightDiff = rootView.rootView.height - (r.bottom - r.top)
            return heightDiff > rootView.rootView.height / 4
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setApplicationContext()
        setArchitectureComponents()
        initializeUI()
    }

    private fun setArchitectureComponents() {
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

    private fun setApplicationContext() {
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

        rootView = main_root_layout

        // Plug in the linear layout manager:
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // Plug in my adapter:
        val adapter = QuoteListAdapter(this, viewModel.quotes.value!!){ quote, position, adapter ->
            println("DebugInfo: Author : \"${quote.author.Name}\" Quote : \"${quote.quoteText}\"")
            quote.isSelected = !quote.isSelected
            adapter.notifyItemChanged(position)
        }
        recyclerView.adapter = adapter

        //Set Observer for RecyclerView source list
        viewModel.getQuotes().observe(this, Observer { quotes ->
            // Update the cached copy of the words in the adapter.
            quotes?.let { adapter.setWords(it) }
        })

        // When button is clicked, instantiate a Quote and add it to DB through the ViewModel
        button_add_quote.setOnClickListener {
            if (isRunning)
                return@setOnClickListener
                isRunning = true

            clearFocus()
            hideSoftKeyboard(it)
            viewModel.viewModelScope.launch {
                if (isKeyboardOnScreen())
                    delay(300)
                if (viewModel.addQuoteSuspend())
                {
                    viewModel.clearEditText()
                    scrollToLastItem(viewModel)
                }
            }

            isRunning = false
        }

        viewModel.viewModelScope.launch {
            viewModel.refreshAllDataSuspend()
            scrollToLastItem(viewModel)
            scrollToLastItemRecyclerView = { scrollToLastItem(viewModel) }
        }
    }

    private fun clearFocus() {
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
