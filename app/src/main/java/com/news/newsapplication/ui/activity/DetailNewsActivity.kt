package com.news.newsapplication.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.news.newsapplication.R
import com.news.newsapplication.data.model.ArticlesItem
import com.news.newsapplication.databinding.ActivityDetailNewsBinding
import com.news.newsapplication.ui.viewmodel.LatestNewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailNewsActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetailNewsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.toolbar.title.text = getString(R.string.details_activity)
        binding.toolbar.materialToolbarBackIcon.visibility = View.VISIBLE

        binding.toolbar.materialToolbarBackIcon.setOnClickListener {
            finish()
        }

        val article = intent.getParcelableExtra<ArticlesItem>("article")
        if (article != null) {
            binding.news = article
            Log.e("onCreate", "Selected article: $article")
        }

    }

}