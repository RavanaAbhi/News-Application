package com.news.newsapplication.ui.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.news.newsapplication.data.Resource
import com.news.newsapplication.data.model.ArticlesItem
import com.news.newsapplication.data.model.News
import com.news.newsapplication.domain.GetItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LatestNewsViewModel(private val getItemsUseCase: GetItemsUseCase,
                          private val application: Application
): AndroidViewModel(application) {

    private val _items = MutableStateFlow<Resource<List<ArticlesItem>>>(Resource.Loading())
    val items: StateFlow<Resource<List<ArticlesItem>>> = _items

    private val _internetAvailable = MutableStateFlow(false)
    val internetAvailable: StateFlow<Boolean> = _internetAvailable

    init {
        val query = "tesla"
        val fromDate = "2024-06-19"
        val toDate = "2024-06-19"
        val sortBy = "publishedAt"
        val apiKey = "f0422484f613437dbe5f9f85107eb38b"
        viewModelScope.launch {
            getItemsUseCase(query, fromDate,toDate, sortBy, apiKey).collect { resource ->
                _items.value = resource
                _internetAvailable.value = isInternetAvailable()
            }
        }
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}