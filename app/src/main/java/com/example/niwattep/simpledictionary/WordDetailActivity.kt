package com.example.niwattep.simpledictionary

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.example.niwattep.simpledictionary.model.RetrieveEntry
import kotlinx.android.synthetic.main.activity_word_detial.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WordDetailActivity : AppCompatActivity() {

    private val wordId: String? by lazy { intent.extras[EXTRA_WORD_ID] as? String }

    companion object {
        private const val EXTRA_WORD_ID = "extra-word-id"

        fun createIntent(context: Context, id: String) = Intent(context, WordDetailActivity::class.java).apply {
            putExtra(EXTRA_WORD_ID, id)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_detial)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        seeMoreButton.setOnClickListener {
            openWebView()
        }
        loadWordDetail()
    }

    private fun openWebView() {
        val uri = Uri.parse("https://www.google.com/search?q=" + wordId)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun loadWordDetail() {
        wordId?.let {
            progressBar.visibility = View.VISIBLE
            getApi().getWord(it).enqueue(object : Callback<RetrieveEntry> {
                override fun onFailure(call: Call<RetrieveEntry>?, t: Throwable?) {
                    progressBar.visibility = View.INVISIBLE
                }

                override fun onResponse(call: Call<RetrieveEntry>?, response: Response<RetrieveEntry>?) {
                    progressBar.visibility = View.INVISIBLE
                    fillResult(response?.body())
                }
            })
        }
    }

    fun fillResult(data: RetrieveEntry?) {
        data?.let {
            it.results?.forEach {
                resultTextView.append(it.word + "\n\n")
                it.lexicalEntries?.forEach {
                    resultTextView.append(it.lexicalCategory + "\n")
                    it.entries?.forEach {
                        it.grammaticalFeatures?.forEach {
                            resultTextView.append(it.text + " ")
                        }
                        resultTextView.append("\n")
                        it.senses?.forEach {
                            it.definitions?.forEach {
                                resultTextView.append(" - " + it + "\n")
                            }
                            resultTextView.append("Example: \n")
                            it.examples?.forEach {
                                resultTextView.append(" - " + it.text + "\n")
                            }
                            resultTextView.append("\n")
                        }
                        resultTextView.append("\n")
                    }
                }
                resultTextView.append("\n")
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getApi() = DictionaryApiService.getApi()
}