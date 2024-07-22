package com.news.newsapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.news.newsapplication.data.model.ArticlesItem

@Database(entities = [ArticlesItem::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun itemDao(): ItemDao
}