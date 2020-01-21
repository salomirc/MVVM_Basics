package com.example.mvvm_basics.ui.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import com.example.mvvm_basics.R
import com.example.mvvm_basics.databinding.FragmentQuoteDetailsBinding
import com.example.mvvm_basics.interfaces.IRootView
import com.example.mvvm_basics.ui.quotes.BaseActivity
import com.example.mvvm_basics.ui.quotes.QuoteDetailsActivity
import com.example.mvvm_basics.ui.quotes.QuotesActivity
import com.example.mvvm_basics.ui.quotes.QuotesViewModel
import com.example.mvvm_basics.utilities.InjectorUtils
import kotlinx.android.synthetic.main.fragment_quote_details.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuoteDetailsFragment : Fragment() {
    private lateinit var fgmView: View
    private lateinit var viewModel: QuotesViewModel
    private var isRunning: Boolean = false
    private var parentForRootView: IRootView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fgmView = inflater.inflate(R.layout.fragment_quote_details, container, false)
        return fgmView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setArchitectureComponents()
        initializeUI()
    }

    private fun setArchitectureComponents() {

        // Get the QuotesViewModelFactory with all of it's dependencies constructed
        val factory = InjectorUtils.provideQuotesViewModelFactory()

        // Use ViewModelProviders class to create / get already created QuotesViewModel
        // for this view (activity)
        viewModel = ViewModelProviders.of(activity!!, factory)
            .get(QuotesViewModel::class.java)

        // Inflate view and obtain an instance of the binding class.
        val binding: FragmentQuoteDetailsBinding = FragmentQuoteDetailsBinding.bind(fgmView)

        // Specify the current activity as the lifecycle owner.
        binding.lifecycleOwner = this

        // Assign the component to a property in the binding class.
        binding.viewmodel = viewModel
    }

    private fun initializeUI(){
        if (activity is QuoteDetailsActivity && !viewModel.isAlreadyInitialized){
            viewModel.quoteText.value = activity!!.intent.extras?.getString("quote") ?: "No quote available ..."
            viewModel.isAlreadyInitialized = true
        }

        addButton.setOnClickListener {
            if (isRunning)
                return@setOnClickListener
            isRunning = true

            viewModel.viewModelScope.launch {
                val baseActivity = activity as BaseActivity
                baseActivity.clearFocus(addCommentsEditText)
                baseActivity.hideSoftKeyboard(it)
                if (baseActivity.isKeyboardOnScreen(parentForRootView!!.rootView))
                    delay(300)
                if (viewModel.addDetailQuoteSuspend())
                {
                    viewModel.clearDetailEditText()
                }
            }

            isRunning = false
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IRootView) parentForRootView = context else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        parentForRootView = null
    }

}
