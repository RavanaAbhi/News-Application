package com.news.newsapplication.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.news.newsapplication.R
import com.news.newsapplication.data.local.ItemDao
import com.news.newsapplication.data.local.ItemEntity
import com.news.newsapplication.data.model.ArticlesItem
import com.news.newsapplication.data.model.News
import com.news.newsapplication.databinding.ActivityDetailNewsBinding
import com.news.newsapplication.ui.viewmodel.LatestNewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.net.URL

@AndroidEntryPoint
class DetailNewsActivity : AppCompatActivity() {

    private val viewModel:LatestNewsViewModel by viewModels()

    private val binding by lazy {
        ActivityDetailNewsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        val intent = intent
        if(intent.hasExtra("articleInfo"))
        {
            var common_article:Any
            var title:String;
            var description:String;
            var author:String;
            var source:String;
            var publishedAt:String;
            var urlToImage:String;
            var url:String
            var isFavourite:Boolean
//            if(intent.getStringExtra("source").equals(UrlConstant.HOME_NEWS_REDIRECTION)) {
//                val article: ArticlesItem = intent.getParcelableExtra<ArticlesItem>("articleInfo")
//                common_article = article
//
//                title = article.title?:""
//                description = article.description?:""
//                author = "by ${article.author?:" Anonymous"}"
//                source = article.source.name?:""
//                publishedAt = article.publishedAt?:""
//                urlToImage = article.urlToImage?:""
//                url = article.url?:""
//            }
//            else {
//                val article: ItemEntity = intent.getParcelableExtra<ItemEntity>("articleInfo")
//                common_article = article
//
//                title = article.title?:""
//                description = article.description?:""
//                author = "by ${article.author}"
//                source = article.sourceId?:""
//                publishedAt = article.publishedAt?:""
//                urlToImage = article.urlToImage?:""
//                url = article.url?:""
//                isFavourite = true
//            }

//            binding.newsDescriptionTitle.text = title
//            binding.newsDescription.text = description
//            binding.textViewbyauthor.text = author
//            binding.textViewbynewspaper.text = source
//            binding.textViewpublished.text = publishedAt


            Glide.with(this@DetailNewsActivity).load("").placeholder(R.drawable.place_holder).into(binding.mainBackdrop)

        }
        else{
            finish()
        }

    }
}