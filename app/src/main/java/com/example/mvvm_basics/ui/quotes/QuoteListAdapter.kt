package com.example.mvvm_basics.ui.quotes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_basics.R
import com.example.mvvm_basics.data.Quote
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class QuoteListAdapter(context: Context,
                       private var quotes : MutableList<Quote>,
                       private val itemClick: (Quote, Int, QuoteListAdapter) -> Unit) :
    RecyclerView.Adapter<QuoteListAdapter.QuoteListHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    inner class QuoteListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view = itemView
        val quoteItemTextView: TextView = itemView.itemTextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteListHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return QuoteListHolder(itemView)
    }

    override fun onBindViewHolder(holder: QuoteListHolder, position: Int) {
        val current = quotes[position]
        holder.quoteItemTextView.text = current.toString()
        holder.quoteItemTextView.setBackgroundResource(
            if (current.isSelected ) R.color.itemTextViewBgColorSelected else R.color.itemTextViewBgColorDefault
        )
        holder.view.setOnClickListener {
            itemClick(current, position, this)
        }
    }

    override fun onViewRecycled(holder: QuoteListHolder) {
        super.onViewRecycled(holder)
        holder.quoteItemTextView.setBackgroundResource(R.color.itemTextViewBgColorDefault)
    }

    internal fun setWords(quotes: MutableList<Quote>) {
        this.quotes = quotes
        notifyDataSetChanged()
    }

    override fun getItemCount() = quotes.size
}
