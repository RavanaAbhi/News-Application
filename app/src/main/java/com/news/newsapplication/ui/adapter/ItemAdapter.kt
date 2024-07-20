package com.news.newsapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.news.newsapplication.data.model.ArticlesItem
import com.news.newsapplication.data.model.News
import com.news.newsapplication.databinding.ItemViewBinding

class ItemAdapter (private val onItemClick: (ArticlesItem) -> Unit) :
    ListAdapter<ArticlesItem, ItemAdapter.ItemViewHolder>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article,onItemClick)

    }

    inner class ItemViewHolder(private val binding: ItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticlesItem,onClick: (ArticlesItem) -> Unit) {
            binding.article = article
            binding.executePendingBindings()
            binding.root.setOnClickListener { onClick(article) }
        }
    }

    class ItemDiffCallback : DiffUtil.ItemCallback<ArticlesItem>() {
        override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean = oldItem.url == newItem.url
        override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean = oldItem == newItem
    }
}