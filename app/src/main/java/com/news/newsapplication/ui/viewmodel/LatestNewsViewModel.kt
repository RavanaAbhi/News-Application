package com.news.newsapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _items = MutableStateFlow<Resource<List<ArticlesItem>>>(Resource.Loading())
    val items: StateFlow<Resource<List<ArticlesItem>>> = _items

    private val _internetAvailable = MutableStateFlow(false)
    val internetAvailable: StateFlow<Boolean> = _internetAvailable

    init {
        val query = "tesla"
        val fromDate = "2024-07-22"
        val toDate = "2024-007-22"
        val sortBy = "publishedAt"
        val apiKey = "f0422484f613437dbe5f9f85107eb38b"

        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                repository.fetchLatestNews(query, fromDate, toDate, sortBy, apiKey).collect { resource ->
                    _items.value = resource
                }
                _internetAvailable.value = true
            } else {
                _internetAvailable.value = false
                // Fetch cached data if no internet
                repository.getCachedNews().collect { resource ->
                    _items.value = resource
                }
            }
        }
    }

}