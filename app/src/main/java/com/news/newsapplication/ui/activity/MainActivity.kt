package com.news.newsapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.news.newsapplication.R
import com.news.newsapplication.data.Resource
import com.news.newsapplication.data.model.ArticlesItem

import com.news.newsapplication.databinding.ActivityMainBinding
import com.news.newsapplication.ui.adapter.ItemAdapter
import com.news.newsapplication.ui.adapter.NewsAdapter
import com.news.newsapplication.ui.viewmodel.LatestNewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: LatestNewsViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.toolbar.title.text = getString(R.string.main_activity)
        binding.toolbar.materialToolbarBackIcon.visibility = View.GONE

        setupRecyclerView()
        observeViewModel()
        setupSearchView()

    }


    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter{ article -> onItemClicked(article)
            Log.e("setupRecyclerView", "Selected article: $article")}
        binding.recyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.items.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.errorText.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                        updateRecyclerView(resource.value)
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.errorText.text = resource.message
                        binding.errorText.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    }
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.errorText.visibility = View.GONE
                        binding.recyclerView.visibility = View.GONE
                    }
                }
            }
        }


    }

    private fun updateRecyclerView(data: List<ArticlesItem>) {
        newsAdapter.submitList(data)
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.searchArticles(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.searchArticles(it) }
                return true
            }
        })
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            viewModel.internetAvailable.collect { isAvailable ->
                if (isAvailable) {
                    Toast.makeText(this@MainActivity, "Internet Available", Toast.LENGTH_SHORT)
                        .show()
//                    binding.internetStatusTextView.text = getString(R.string.internet_available)
//                    binding.internetStatusTextView.visibility = View.VISIBLE
                } else {
                    Toast.makeText(this@MainActivity, "Internet Not Available", Toast.LENGTH_SHORT)
                        .show()
//                    binding.internetStatusTextView.visibility = View.GONE
                }
            }
        }
    }

    private fun onItemClicked(item: ArticlesItem) {
        Log.e("onItemClicked", "Selected article: $item")
        val intent = Intent(this, DetailNewsActivity::class.java)
        intent.putExtra("article", item)
        startActivity(intent)
    }


    override fun onDestroy() {
        super.onDestroy()
    }

}