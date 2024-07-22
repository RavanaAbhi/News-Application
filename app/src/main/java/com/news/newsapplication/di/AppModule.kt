package com.news.newsapplication.di

import android.content.Context
import androidx.room.Room
import com.news.newsapplication.utils.Retrofit
import com.news.newsapplication.data.local.AppDataBase
import com.news.newsapplication.data.local.ItemDao
import com.news.newsapplication.data.remote.service.ApiService
import com.news.newsapplication.data.repository.ItemRepositoryImpl
import com.news.newsapplication.domain.ItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService? {
        return Retrofit.getClient()?.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDataBase {
        return Room.databaseBuilder(
            appContext,
            AppDataBase::class.java,
            "item_database"
        ).build()
    }

    @Provides
    fun provideItemDao(database: AppDataBase): ItemDao {
        return database.itemDao()
    }

    @Provides
    @Singleton
    fun provideItemRepository(
        apiService: ApiService,
        itemDao: ItemDao,
        @ApplicationContext context: Context
    ): ItemRepository {
        return ItemRepositoryImpl(apiService, itemDao, context)
    }
}

