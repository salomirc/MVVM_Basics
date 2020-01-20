package com.example.mvvm_basics.ui.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm_basics.R
import com.example.mvvm_basics.databinding.FragmentQuotesBinding
import com.example.mvvm_basics.ui.quotes.QuoteDetails
import com.example.mvvm_basics.ui.quotes.QuoteListAdapter
import com.example.mvvm_basics.ui.quotes.QuotesActivity
import com.example.mvvm_basics.ui.quotes.QuotesViewModel
import com.example.mvvm_basics.utilities.InjectorUtils
import kotlinx.android.synthetic.main.activity_quotes.*
import kotlinx.android.synthetic.main.fragment_quotes.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuotesFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null
    private lateinit var viewModel: QuotesViewModel
    private var isRunning: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get the QuotesViewModelFactory with all of it's dependencies constructed
        val factory = InjectorUtils.provideQuotesViewModelFactory()
        // Use ViewModelProviders class to create / get already created QuotesViewModel
        // for this view (activity)
        viewModel = ViewModelProviders.of(activity!!, factory)
            .get(QuotesViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_quotes, container, false)

        // Obtain an instance of the binding class.
        val binding: FragmentQuotesBinding = FragmentQuotesBinding.bind(view)

        // Specify the current activity as the lifecycle owner.
        binding.lifecycleOwner = this

        // Assign the component to a property in the binding class.
        binding.viewmodel = viewModel

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initializeUI()
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    private fun initializeUI() {

        // Plug in the linear layout manager:
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager

        // Plug in my adapter:
        val adapter = QuoteListAdapter(activity!!, viewModel.quotes.value!!){ quote, position, adapter ->
            println("DebugInfo: Author : \"${quote.author.Name}\" Quote : \"${quote.quoteText}\"")
            quote.isSelected = !quote.isSelected
            adapter.notifyItemChanged(position)
            if (quote.isSelected){
                val intent = Intent(activity, QuoteDetails::class.java)
                intent.putExtra("quote", quote.toString())
                startActivity(intent)
            }
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

            viewModel.viewModelScope.launch {
                val quotesActivity: QuotesActivity = activity as QuotesActivity
                quotesActivity.clearFocus(editText_author, editText_quote)
                quotesActivity.clearFocus(editText_quote, editText_author)
                quotesActivity.hideSoftKeyboard(it)
                if (quotesActivity.isKeyboardOnScreen((activity as QuotesActivity).root_view_quotes))
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
            (activity as QuotesActivity).scrollToLastItemRecyclerView = { scrollToLastItem(viewModel) }
        }
    }

    private fun scrollToLastItem(viewModel: QuotesViewModel) {
        recyclerView.scrollToPosition(viewModel.quotes.value!!.size - 1)
    }
}
