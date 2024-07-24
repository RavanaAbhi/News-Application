package com.news.newsapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.news.newsapplication.data.model.ArticlesItem
import com.news.newsapplication.databinding.ItemViewBinding

class NewsAdapter(private val OnItemClicked: (ArticlesItem) -> Unit) : ListAdapter<ArticlesItem,
        NewsAdapter.ArticleViewHolder>(ArticleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding, OnItemClicked)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    class ArticleViewHolder(private val binding: ItemViewBinding,
                            private val onItemClicked: (ArticlesItem) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: ArticlesItem) {
            binding.article = article

            binding.root.setOnClickListener {
                onItemClicked(article)
            }
        }
    }

    class ArticleDiffCallback : DiffUtil.ItemCallback<ArticlesItem>() {
        override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem == newItem
        }
    }
}