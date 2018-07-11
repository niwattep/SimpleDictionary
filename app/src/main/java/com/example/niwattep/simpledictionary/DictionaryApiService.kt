package com.example.niwattep.simpledictionary

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DictionaryApiService {

    companion object {
        private const val BASE_URL = "https://od-api.oxforddictionaries.com/api/v1/"
        private const val APP_KEY = "5e2cfe9aa5abbaa59fce915365cc5879"
        private const val APP_ID = "8723ce2a"
        private const val RESPONSE_TYPE = "application/json"

        private fun getHttpClient() = OkHttpClient.Builder().apply {
            addInterceptor { chain ->
                val request = chain.request().newBuilder().apply {
                    addHeader("Accept", RESPONSE_TYPE)
                    addHeader("app_id", APP_ID)
                    addHeader("app_key", APP_KEY)
                }.build()
                chain.proceed(request)
            }
        }.build()

        fun getApi() = Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            client(getHttpClient())
        }.build().create(DictionaryAPI::class.java)
    }
}