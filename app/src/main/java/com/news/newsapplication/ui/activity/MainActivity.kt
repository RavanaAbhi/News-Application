package com.news.newsapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
import com.news.newsapplication.ui.viewmodel.LatestNewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: LatestNewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.toolbar.materialToolbarBackIcon.visibility = View.GONE
        binding.toolbar.materialToolbarBackIcon.title = R.string.main_activity.toString()

        lifecycleScope.launch {
            viewModel.items.collect { resource ->
                when(resource) {
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                        binding.errorText.visibility = View.VISIBLE
                    }
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.errorText.visibility = View.GONE

                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.recyclerView.visibility = View.GONE
                        binding.errorText.visibility = View.GONE
//                        binding.errorText.text = resource.value.toString()
                        recyclerView(resource.value)
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.internetAvailable.collect { available ->
                if (available) {
                    Toast.makeText(this@MainActivity, "Internet Available", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }





    private fun recyclerView(resource: List<ArticlesItem>) = binding.recyclerView.apply {
        adapter = ItemAdapter(this@MainActivity,resource)
        binding.recyclerView.adapter = adapter
    }
}