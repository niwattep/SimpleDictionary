package com.example.niwattep.simpledictionary

import com.example.niwattep.simpledictionary.model.RetrieveEntry
import com.example.niwattep.simpledictionary.model.WordListResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DictionaryAPI {
    @GET("search/en")
    fun getSearchResult(@Query("q") q: String): Call<WordListResult>

    @GET("entries/en/{word_id}")
    fun getWord(@Path("word_id") id: String): Call<RetrieveEntry>
}