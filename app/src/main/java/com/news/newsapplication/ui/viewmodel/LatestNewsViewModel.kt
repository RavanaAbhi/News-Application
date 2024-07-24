package com.news.newsapplication.ui.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.news.newsapplication.BuildConfig
import com.news.newsapplication.data.Resource
import com.news.newsapplication.data.model.ArticlesItem
import com.news.newsapplication.data.repository.ItemRepository
import com.news.newsapplication.network.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LatestNewsViewModel @Inject constructor (
    private val repository: ItemRepository,
    private val networkHelper: NetworkHelper
): ViewModel() {

    private val _allItems = mutableListOf<ArticlesItem>()
    private val _items = MutableStateFlow<Resource<List<ArticlesItem>>>(Resource.Loading())
    val items: StateFlow<Resource<List<ArticlesItem>>> = _items

    private val _internetAvailable = MutableStateFlow(false)
    val internetAvailable: StateFlow<Boolean> = _internetAvailable

//    private val _selectedArticle = MutableStateFlow<ArticlesItem?>(null)
//    val selectedArticle: StateFlow<ArticlesItem?> = _selectedArticle

    private val _userProfile = MutableLiveData<ArticlesItem?>()
    val userProfile: LiveData<ArticlesItem?> get() = _userProfile


    init {
       fetchNewsData()
    }

    private fun fetchNewsData(){
        val query = "apple"
        val fromDate = "2024-07-22"
        val toDate = "2024-07-22"
        val sortBy = "popularity"
        val apiKey = BuildConfig.API_KEY

        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                repository.fetchLatestNews(query, fromDate, toDate, sortBy, apiKey).collect { resource ->
                    _items.value = resource
                    if (resource is Resource.Success) {
                        _allItems.clear()

                        _allItems.addAll(resource.value)

                        viewModelScope.launch {
                            repository.cacheNews(resource.value.map {
                                ArticlesItem(it.id, it.author, it.url, it.title, it.description, it.urlToImage,it.publishedAt, it.content)
                            })
                        }
                    }
                }
                _internetAvailable.value = true

            } else {
                _internetAvailable.value = false
                repository.getCachedNews().collect { resource ->
                    if (resource is Resource.Success) {
                        _allItems.clear()
                        _allItems.addAll(resource.value)
                    }
                    _items.value = resource
                }
            }
        }
    }

    fun selectArticle(article: ArticlesItem) {
        _userProfile.value = article
        Log.e("selectArticle", "Selected article: $article")
    }

    fun searchArticles(query: String) {
        val filteredItems = _allItems.filter { it.title?.contains(query, ignoreCase = true) == true || it.description?.contains(query, ignoreCase = true) == true }
        _items.value = Resource.Success(filteredItems)
    }


}