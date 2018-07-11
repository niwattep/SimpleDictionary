package com.example.niwattep.simpledictionary

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.niwattep.simpledictionary.model.WordListResult
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var adapter: WordListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchButton.setOnClickListener {
            loadSearchResult()
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = WordListAdapter(this).apply {
            words = null
        }
        recyclerView.apply {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        }
    }

    private fun getApi() = DictionaryApiService.getApi()

    private fun loadSearchResult() {
        val q = inputEditText.text.toString()
        if (q.isNotBlank()) {
            progressBar.visibility = View.VISIBLE
            getApi().getSearchResult(q).enqueue(object : Callback<WordListResult> {
                override fun onFailure(call: Call<WordListResult>?, t: Throwable?) {
                    progressBar.visibility = View.INVISIBLE
                }

                override fun onResponse(call: Call<WordListResult>?, response: Response<WordListResult>?) {
                    progressBar.visibility = View.INVISIBLE
                    val wordList = response?.body()
                    adapter?.words = wordList?.results
                    adapter?.notifyDataSetChanged()
                }
            })
        }
    }
}
