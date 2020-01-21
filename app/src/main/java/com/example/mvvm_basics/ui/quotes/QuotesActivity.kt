package com.example.mvvm_basics.ui.quotes

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.ViewGroup
import com.example.mvvm_basics.R
import com.example.mvvm_basics.interfaces.IRootView
import kotlinx.android.synthetic.main.activity_quotes.*

class QuotesActivity : BaseActivity(), IRootView {

    override lateinit var rootView: ViewGroup
    var scrollToLastItemRecyclerView: (() -> Unit)? = null
//    private var isRunning: Boolean = false

    companion object AppContext {
        lateinit var ApplicationContext: Context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setApplicationContext()
        setContentView(R.layout.activity_quotes)
//        setArchitectureComponents()
//        initializeUI()
        rootView = root_view_quotes
    }

//    private fun setArchitectureComponents() {
//        // Get the QuotesViewModelFactory with all of it's dependencies constructed
//        val factory = InjectorUtils.provideQuotesViewModelFactory()
//        // Use ViewModelProviders class to create / get already created QuotesViewModel
//        // for this view (activity)
//        viewModel = ViewModelProviders.of(this, factory)
//            .get(QuotesViewModel::class.java)
//
//        // Inflate view and obtain an instance of the binding class.
//        val binding: ActivityQuotesBinding =
//            DataBindingUtil.setContentView(this, R.layout.activity_quotes)
//
//        // Specify the current activity as the lifecycle owner.
//        binding.lifecycleOwner = this
//
//        // Assign the component to a property in the binding class.
//        binding.viewmodel = viewModel
//    }

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

//    private fun initializeUI() {
//
//        // Plug in the linear layout manager:
//        val layoutManager = LinearLayoutManager(this)
//        recyclerView.layoutManager = layoutManager
//
//        // Plug in my adapter:
//        val adapter = QuoteListAdapter(this, viewModel.quotes.value!!){ quote, position, adapter ->
//            println("DebugInfo: Author : \"${quote.author.Name}\" Quote : \"${quote.quoteText}\"")
//            quote.isSelected = !quote.isSelected
//            adapter.notifyItemChanged(position)
//            if (quote.isSelected){
//                val intent = Intent(this, QuoteDetails::class.java)
//                intent.putExtra("quote", quote.toString())
//                startActivity(intent)
//            }
//        }
//        recyclerView.adapter = adapter
//
//        //Set Observer for RecyclerView source list
//        viewModel.getQuotes().observe(this, Observer { quotes ->
//            // Update the cached copy of the words in the adapter.
//            quotes?.let { adapter.setWords(it) }
//        })
//
//        // When button is clicked, instantiate a Quote and add it to DB through the ViewModel
//        button_add_quote.setOnClickListener {
//            if (isRunning)
//                return@setOnClickListener
//                isRunning = true
//
//            viewModel.viewModelScope.launch {
//                clearFocus(editText_quote, editText_author)
//                hideSoftKeyboard(it)
//                if (isKeyboardOnScreen(root_view_quotes))
//                    delay(300)
//                if (viewModel.addQuoteSuspend())
//                {
//                    viewModel.clearEditText()
//                    scrollToLastItem(viewModel)
//                }
//            }
//
//            isRunning = false
//        }
//
//        viewModel.viewModelScope.launch {
//            viewModel.refreshAllDataSuspend()
//            scrollToLastItem(viewModel)
//            scrollToLastItemRecyclerView = { scrollToLastItem(viewModel) }
//        }
//    }
//
//    private fun scrollToLastItem(viewModel: QuotesViewModel) {
//        recyclerView.scrollToPosition(viewModel.quotes.value!!.size - 1)
//    }
}
