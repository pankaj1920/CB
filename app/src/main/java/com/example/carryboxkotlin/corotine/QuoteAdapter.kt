package com.example.kotlinretrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carryboxkotlin.R
import com.example.kotlinretrofit.model.QuoteData

class QuoteAdapter(val quoteData:List<QuoteData>): RecyclerView.Adapter<Quote_VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Quote_VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.quote_item,parent,false)
        return Quote_VH(view)
    }

    override fun onBindViewHolder(holder: Quote_VH, position: Int) {
        val data:QuoteData = quoteData.get(position)
        holder.quoteText.text = data.quote
        holder.quoteByText.text = data.author

    }

    override fun getItemCount(): Int {
       return quoteData.size
    }
}

class Quote_VH(itemView: View): RecyclerView.ViewHolder(itemView) {
    val quoteText:TextView = itemView.findViewById(R.id.quoteText)
    val quoteByText:TextView = itemView.findViewById(R.id.quoteByText)
}

interface QuoteItemClicked{
    fun onItemClicked(item:String)
}