package com.example.mvvm_basics.ui.quotes

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.mvvm_basics.R
import com.example.mvvm_basics.interfaces.IRootView
import com.example.mvvm_basics.ui.fragments.QuoteDetailsFragment
import com.example.mvvm_basics.ui.fragments.QuotesFragment
import com.example.mvvm_basics.utilities.QUOTES_DETAILS_FRAGMENT_TAG
import com.example.mvvm_basics.utilities.QUOTES_FRAGMENT_TAG
import kotlinx.android.synthetic.main.activity_quotes.*

class QuotesActivity : BaseActivity(), IRootView {

    override lateinit var rootView: ViewGroup

    companion object AppContext {
        lateinit var ApplicationContext: Context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setApplicationContext()
        setContentView(R.layout.activity_quotes)
        rootView = root_view_quotes


        val fragmentManager = supportFragmentManager
        val quoteFragment = QuotesFragment()
        val quoteDetailsFragment = QuoteDetailsFragment()
        val transaction = fragmentManager.beginTransaction()

        if (savedInstanceState != null) {
            fragmentManager.popBackStackImmediate()
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                transaction.replace(R.id.quoteDetailsFrame, quoteDetailsFragment, QUOTES_DETAILS_FRAGMENT_TAG)
            }
        }
        else{
            transaction.add(R.id.quotesFrame, quoteFragment, QUOTES_FRAGMENT_TAG)
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                transaction.add(R.id.quoteDetailsFrame, quoteDetailsFragment, QUOTES_DETAILS_FRAGMENT_TAG)
            }
        }
        transaction.commit()
    }

    private fun setApplicationContext() {
        ApplicationContext = this.applicationContext
    }
}
