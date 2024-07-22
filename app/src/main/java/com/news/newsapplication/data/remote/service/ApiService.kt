package com.news.newsapplication.data.remote.service

import com.news.newsapplication.data.model.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("everything")
    suspend fun fetchLatestNews(@Query("q") query: String,
                                @Query("from") fromDate: String,
                                @Query("to") toDate: String,
                                @Query("sortBy") sortBy: String,
                                @Query("apiKey") apiKey: String): Response<News>

}