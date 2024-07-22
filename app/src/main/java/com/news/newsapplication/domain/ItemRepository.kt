package com.news.newsapplication.domain

import com.news.newsapplication.data.Resource
import com.news.newsapplication.data.model.ArticlesItem
import com.news.newsapplication.data.model.News
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
interface ItemRepository {

    suspend fun fetchLatestNews(
        query: String,
        fromDate: String,
        toDate : String,
        sortBy: String,
        apiKey: String
    ): Flow<Resource<List<ArticlesItem>>>
}