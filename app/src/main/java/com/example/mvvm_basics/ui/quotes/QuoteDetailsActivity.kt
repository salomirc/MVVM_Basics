package com.example.mvvm_basics.ui.quotes

import android.os.Bundle
import android.view.ViewGroup
import com.example.mvvm_basics.R
import com.example.mvvm_basics.interfaces.IRootView
import kotlinx.android.synthetic.main.activity_quote_details.*

class QuoteDetailsActivity : BaseActivity(), IRootView {

    override lateinit var rootView: ViewGroup
//    private var isRunning: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setArchitectureComponents()
//        initializeUI()
        setContentView(R.layout.activity_quote_details)
        rootView = root_view_quotes_details
    }

//    private fun setArchitectureComponents() {
//        // Get the QuotesViewModelFactory with all of it's dependencies constructed
//        val factory = InjectorUtils.provideQuotesDetailsViewModelFactory()
//        // Use ViewModelProviders class to create / get already created QuotesViewModel
//        // for this view (activity)
//        viewModel = ViewModelProviders.of(this, factory)
//            .get(QuoteDetailsViewModel::class.java)
//
//        // Inflate view and obtain an instance of the binding class.
//        val binding: ActivityQuoteDetailsBinding =
//            DataBindingUtil.setContentView(this, R.layout.activity_quote_details)
//
//        // Specify the current activity as the lifecycle owner.
//        binding.lifecycleOwner = this
//
//        // Assign the component to a property in the binding class.
//        binding.viewmodel = viewModel
//    }
//
//    private fun initializeUI(){
//        if (!viewModel.isAlreadyInitialized) {
//            viewModel.quoteText.value = intent.extras?.getString("quote") ?: "No quote available ..."
//            viewModel.isAlreadyInitialized = true
//        }
//
//        addButton.setOnClickListener {
//            if (isRunning)
//                return@setOnClickListener
//            isRunning = true
//
//            viewModel.viewModelScope.launch {
//                clearFocus(addCommentsEditText)
//                hideSoftKeyboard(it)
//                if (isKeyboardOnScreen(root_view_quote_details))
//                    delay(300)
//                if (viewModel.addQuoteSuspend())
//                {
//                    viewModel.clearEditText()
//                }
//            }
//
//            isRunning = false
//        }
//    }
}
