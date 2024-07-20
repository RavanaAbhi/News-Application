package com.news.newsapplication.domain

import com.news.newsapplication.data.Resource
import com.news.newsapplication.data.model.ArticlesItem
import com.news.newsapplication.data.model.News
import kotlinx.coroutines.flow.Flow

class GetItemsUseCase(private val repository: ItemRepository) {
    suspend operator fun invoke(
        query: String,
        fromDate: String,
        toDate : String,
        sortBy: String,
        apiKey: String
    ): Flow<Resource<List<ArticlesItem>>> = repository.fetchLatestNews(query, fromDate,toDate, sortBy, apiKey)


}