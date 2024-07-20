package com.news.newsapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope

import com.news.newsapplication.databinding.ActivityMainBinding
import com.news.newsapplication.ui.adapter.ItemAdapter
import com.news.newsapplication.ui.viewmodel.LatestNewsViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: LatestNewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        enableEdgeToEdge()

        binding.toolbar.materialToolbarBackIcon.visibility = View.GONE


        val adapter = ItemAdapter { _ ->
            val intent = Intent(this, DetailNewsActivity::class.java)
            startActivity(intent)
        }
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.items.collect { items ->
                adapter.submitList(items)
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
}