package com.news.newsapplication

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


const val SPLASH_DELAY = 3000
const val BASE_URL = "https://newsapi.org/v2/everything?q=apple"
const val NEWS_API_KEY = "f0422484f613437dbe5f9f85107eb38b"
const val SORT_BY = "popularity"
const val DATE_FROM = "from=2024-07-18"
const val DATE_TO = "to=2024-07-18"

class Retrofit {

    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit? {
        if (retrofit == null) {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .build()

            val gson = GsonBuilder()
                .setLenient()
                .create()

            retrofit = Retrofit.Builder()
                .baseUrl("https://thebestiesapp.com/mobile/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return retrofit
    }

}