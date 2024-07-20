package com.news.newsapplication.domain

import com.news.newsapplication.data.model.ArticlesItem
import com.news.newsapplication.data.model.News
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    suspend fun fetchLatestNews(
        query: String,
        fromDate: String,
        toDate : String,
        sortBy: String,
        apiKey: String
    ): Flow<List<ArticlesItem>>
}