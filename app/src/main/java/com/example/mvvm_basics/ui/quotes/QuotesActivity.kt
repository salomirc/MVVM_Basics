package com.example.mvvm_basics.ui.quotes

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import com.example.mvvm_basics.R
import com.example.mvvm_basics.interfaces.IRootView
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
    }

    private fun setApplicationContext() {
        ApplicationContext = this.applicationContext
    }
}
