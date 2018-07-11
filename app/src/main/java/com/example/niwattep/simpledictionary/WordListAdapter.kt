package com.example.niwattep.simpledictionary

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.niwattep.simpledictionary.model.WordListResultItem
import kotlinx.android.synthetic.main.view_word_result_item.view.*

class WordListAdapter(val context: Context) : RecyclerView.Adapter<WordListAdapter.WordListItemViewHolder>() {
    var words: ArrayList<WordListResultItem>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            WordListItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_word_result_item, parent, false))

    override fun getItemCount(): Int = words?.size ?: 0

    override fun onBindViewHolder(holder: WordListItemViewHolder, position: Int) {
        holder.bind(words?.get(position))
    }

    inner class WordListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(word: WordListResultItem?) {
            word?.let { word ->
                itemView.setOnClickListener {
                    context.startActivity(WordDetailActivity.createIntent(context, word.id))
                }
                itemView.wordTextView.text = word.word
            }
        }
    }
}