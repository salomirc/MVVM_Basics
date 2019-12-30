package com.example.mvvm_basics.ui.quotes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_basics.R
import com.example.mvvm_basics.data.Quote

class QuoteListAdapter(context: Context) : RecyclerView.Adapter<QuoteListAdapter.WordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var quotes = emptyList<Quote>() // Cached copy of quotes

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = quotes[position]
        holder.wordItemView.text = current.toString()
    }

    internal fun setWords(quotes: List<Quote>) {
        this.quotes = quotes
        notifyDataSetChanged()
    }

    override fun getItemCount() = quotes.size
}
