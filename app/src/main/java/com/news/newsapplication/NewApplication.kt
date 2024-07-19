package com.news.newsapplication

import android.app.Application

class NewApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

//        appComponent = DaggerAppComponent.builder()
//            .appModule(AppModule())
//            .build()
    }
}