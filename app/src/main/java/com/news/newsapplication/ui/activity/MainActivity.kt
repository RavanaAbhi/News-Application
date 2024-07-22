package com.news.newsapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
                        binding.errorText.visibility = View.GONE
                    }
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.errorText.visibility = View.GONE
//                        adapter.submitList(resource)
//                        recyclerView(resource)
                        Log.d("LatestNewsViewModel", "Loading...$resource")
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.recyclerView.visibility = View.GONE
                        binding.errorText.visibility = View.VISIBLE
                        binding.errorText.text = resource.value.toString()
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


        val adapter = ItemAdapter { _ ->
            val intent = Intent(this, DetailNewsActivity::class.java)
//            intent.putExtra("article", article)
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.recyclerView.adapter = adapter

    }





//    private fun recyclerView(resource: Resource.Loading<List<ArticlesItem>>) = binding.recyclerView.apply {
//        adapter = ItemAdapter(resource)
//        binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
//        binding.recyclerView.adapter = adapter
//    }
}