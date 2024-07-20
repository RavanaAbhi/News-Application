package com.news.newsapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.news.newsapplication.data.model.News
import com.news.newsapplication.databinding.ItemViewBinding

class ItemAdapter (private val onItemClick: (News) -> Unit) :
    ListAdapter<News, ItemAdapter.ItemViewHolder>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(private val binding: ItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: News) {
//            binding.itemName.text = item.name
            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    class ItemDiffCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean = oldItem.totalResults == newItem.totalResults
        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean = oldItem == newItem
    }
}