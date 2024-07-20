package com.news.newsapplication.data.repository

import android.content.Context
import android.net.ConnectivityManager
import com.news.newsapplication.data.Resource
import com.news.newsapplication.data.model.News
import com.news.newsapplication.data.local.ItemDao
import com.news.newsapplication.data.local.ItemEntity
import com.news.newsapplication.data.model.ArticlesItem
import com.news.newsapplication.data.model.Source
import com.news.newsapplication.data.remote.service.ApiService
import com.news.newsapplication.domain.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val articleDao: ItemDao,
    private val context: Context
) : ItemRepository {

    override suspend fun fetchLatestNews(
        query: String,
        fromDate: String,
        toDate : String,
        sortBy: String,
        apiKey: String
    ): Flow<List<ArticlesItem>> = flow {
        emit(Resource.Loading())

        if (isInternetAvailable()) {
            try {
                val apiResponse = apiService.fetchLatestNews(query, fromDate, sortBy, toDate,apiKey)
                val apiArticles = apiResponse.articles
                val localArticles = articleDao.getAllArticles().first().map { it.toDomain() }
                if (apiArticles != localArticles) {
                    articleDao.insertAll(apiArticles.map { it.toEntity() })
                    emit(Resource.Success(apiArticles))
                } else {
                    emit(Resource.Success(localArticles))
                }
            } catch (e: Exception) {
                emit(Resource.Error("Failed to fetch data from API"))
            }
        } else {
            try {
                val localArticles = articleDao.getAllArticles().map { it.toDomain() }
                emit(Resource.Success(localArticles))
            } catch (e: Exception) {
                emit(Resource.Error("Failed to fetch data from local database"))
            }
        }
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    // Extensions to convert between Article and ArticleEntity
    private fun ItemEntity.toDomain(): ArticlesItem {
        return ArticlesItem(
            source = Source(sourceId!!, sourceName!!),
            author = author!!,
            title = title!!,
            description = description!!,
            url = url!!,
            urlToImage = urlToImage!!,
            publishedAt = publishedAt,
            content = content!!
        )
    }

    private fun ArticlesItem.toEntity(): ItemEntity {
        return ItemEntity(
            url = url,
            sourceId = source.id,
            sourceName = source.name,
            author = author,
            title = title,
            description = description,
            urlToImage = urlToImage,
            publishedAt = publishedAt,
            content = content
        )
    }
}

