package com.example.mvvm_basics.ui.quotes

import android.os.Bundle
import android.view.ViewGroup
import com.example.mvvm_basics.R
import com.example.mvvm_basics.interfaces.IRootView
import kotlinx.android.synthetic.main.activity_quote_details.*

class QuoteDetailsActivity : BaseActivity(), IRootView {

    override lateinit var rootView: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quote_details)
        rootView = root_view_quotes_details
    }
}
