package com.news.newsapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ItemEntity::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun itemDao(): ItemDao

    fun getInstance(context: Context): RoomDatabase{
        return Room.databaseBuilder(context, AppDataBase::class.java, "app_database")
            .build()
    }
}