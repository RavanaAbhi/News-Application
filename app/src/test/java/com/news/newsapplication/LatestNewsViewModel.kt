package com.news.newsapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.news.newsapplication.data.Resource
import com.news.newsapplication.data.model.ArticlesItem
import com.news.newsapplication.data.repository.ItemRepository
import com.news.newsapplication.ui.viewmodel.LatestNewsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@ExperimentalCoroutinesApi
class MainViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var repository: ItemRepository

    @Test
    fun fetchArticles_success() = runBlockingTest {
        val articles = listOf(
            ArticlesItem(
                1,
                "author1",
                "title1",
                "description1",
                "url1",
                "urlToImage1",
                "publishedAt1",
                "content1",
            ),
            ArticlesItem(
                2,
                "title2",
                "author2",
                "description2",
                "url2",
                "urlToImage2",
                "publishedAt2",
                "content2"
            )
        )

        `when`(repository.getCachedNews()).thenReturn(flow { emit(Resource.Success(articles)) })

//        val viewModel = LatestNewsViewModel(repository)
//
//        viewModel.items.collect { resource ->
//            assert(resource is Resource.Success && resource.value == articles)
//        }
    }

    @Test
    fun fetchArticles_error() = runBlockingTest {
        val errorMessage = "An error occurred"
//        `when`(repository.getCachedNews()).thenReturn(flow { emit(Error(errorMessage)) })
//
//        val viewModel = LatestNewsViewModel(repository)
//
//        viewModel.items.collect { resource ->
//            assert(resource is Error && resource.message == errorMessage)
//        }
    }
}