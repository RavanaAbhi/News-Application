package com.news.newsapplication.data.repository

import android.util.Log
import com.news.newsapplication.data.Resource
import com.news.newsapplication.data.Resource.Error
import com.news.newsapplication.data.Resource.Loading
import com.news.newsapplication.data.Resource.Success
import com.news.newsapplication.data.local.ItemDao
import com.news.newsapplication.data.model.ArticlesItem
import com.news.newsapplication.data.remote.service.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ItemRepository @Inject constructor(
    private val apiService: ApiService,
    private val itemDao: ItemDao
) {
    suspend fun fetchLatestNews(query: String, fromDate: String, toDate: String, sortBy: String, apiKey: String): Flow<Resource<List<ArticlesItem>>> {
        return flow {
            emit(Loading())
            try {
                val response = apiService.fetchLatestNews(query, fromDate, toDate, sortBy, apiKey)
                if (response.isSuccessful) {
                    val articles = response.body()?.articles
                    if (!articles.isNullOrEmpty()) {
                        itemDao.insertAll(articles)
                        emit(Success(articles))
                        Log.e("fetchLatestNews",""+articles)
                    } else {
                        emit(Error("No articles found"))
                        Log.e("fetchLatestNews",""+articles)
                    }
                } else {
                    emit(Error("API call failed with response code: ${response.code()}"))
                }
            } catch (e: Exception) {
                emit(Error("Network error: ${e.localizedMessage}"))
            }
        }
    }

    suspend fun cacheNews(articles: List<ArticlesItem>) {
        itemDao.insertAll(articles)
    }

    suspend fun getCachedNews(): Flow<Resource<List<ArticlesItem>>> = flow {
        emit(Loading())
        try {
            val cachedArticles = itemDao.getAllArticles()
            emit(Success(cachedArticles))
        } catch (e: Exception) {
            emit(Error(e.message ?: "Unknown error"))
        }
    }
}