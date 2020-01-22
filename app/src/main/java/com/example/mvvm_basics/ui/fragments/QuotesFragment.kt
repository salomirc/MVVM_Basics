package com.example.mvvm_basics.ui.fragments


import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm_basics.R
import com.example.mvvm_basics.databinding.FragmentQuotesBinding
import com.example.mvvm_basics.ui.quotes.QuoteListAdapter
import com.example.mvvm_basics.ui.quotes.QuotesActivity
import com.example.mvvm_basics.ui.quotes.QuotesViewModel
import com.example.mvvm_basics.utilities.InjectorUtils
import com.example.mvvm_basics.utilities.QUOTES_DETAILS_FRAGMENT_TAG
import com.example.mvvm_basics.utilities.QUOTES_FRAGMENT_TAG
import kotlinx.android.synthetic.main.activity_quotes.*
import kotlinx.android.synthetic.main.fragment_quotes.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuotesFragment : Fragment() {

    private lateinit var viewModel: QuotesViewModel
    private lateinit var fgmView: View
    private var isRunning: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fgmView = inflater.inflate(R.layout.fragment_quotes, container, false)
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
        val binding: FragmentQuotesBinding = FragmentQuotesBinding.bind(fgmView)

        // Specify the current activity as the lifecycle owner.
        binding.lifecycleOwner = this

        // Assign the component to a property in the binding class.
        binding.viewmodel = viewModel
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
                viewModel.quoteText.value = quote.toString()
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                {
//                    viewModel.quoteText.value = quote.toString()
//                    val intent = Intent(activity, QuoteDetailsActivity::class.java)
//                    intent.putExtra("quote", quote.toString())
//                    startActivity(intent)

                    val fragmentManager = activity!!.supportFragmentManager
                    val quoteDetailsFragment = QuoteDetailsFragment()
                    val quotesFragment =fragmentManager.findFragmentByTag(QUOTES_FRAGMENT_TAG)
                    val transaction = fragmentManager.beginTransaction()
                    transaction.replace(R.id.quotesFrame, quoteDetailsFragment, QUOTES_DETAILS_FRAGMENT_TAG)
//                    transaction.add(R.id.quotesFrame, quoteDetailsFragment, QUOTES_DETAILS_FRAGMENT_TAG)
//                    transaction.hide(quotesFragment!!)
//                    transaction.show(quoteDetailsFragment)
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
//                else if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
//                {
//                    viewModel.quoteText.value = quote.toString()
//                }
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
            viewModel.viewModelScope.launch {
                if (isRunning)
                    return@launch

                isRunning = true
                val quotesActivity = activity as QuotesActivity
                quotesActivity.clearFocus(editText_quote, editText_author)
                quotesActivity.hideSoftKeyboard(it)
                if (quotesActivity.isKeyboardOnScreen(quotesActivity.root_view_quotes))
                    delay(300)
                if (viewModel.addQuoteSuspend())
                {
                    viewModel.clearEditText()
                    scrollToLastItem(viewModel)
                }

                isRunning = false
            }
        }

        viewModel.viewModelScope.launch {
            viewModel.refreshAllDataSuspend()
            scrollToLastItem(viewModel)
        }
    }

    private fun scrollToLastItem(viewModel: QuotesViewModel) {
        recyclerView.scrollToPosition(viewModel.quotes.value!!.size - 1)
    }
}
