package com.news.newsapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.news.newsapplication.data.model.ArticlesItem

@Dao
interface ItemDao {
    @Query("SELECT * FROM articles")
    fun getAllArticles(): List<ArticlesItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<ArticlesItem>)
}