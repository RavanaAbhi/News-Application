package com.news.newsapplication.data

sealed class Resource <T>{
    data class Success<T>(val value: T) : Resource<T>()
    data class Error<T>(val message: String) : Resource<T>()
    class Loading<T> : Resource<T>()

}
