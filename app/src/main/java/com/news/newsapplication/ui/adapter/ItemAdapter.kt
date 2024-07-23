package com.news.newsapplication.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.news.newsapplication.data.model.ArticlesItem
import com.news.newsapplication.databinding.ItemViewBinding
import com.news.newsapplication.ui.activity.DetailNewsActivity

class ItemAdapter (val context: Context,private val article: List<ArticlesItem>/*private val onItemClick: (ArticlesItem) -> Unit*/) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(/*ItemDiffCallback()*/) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val article = article[position]

        holder.listLayoutBinding.article = article

    }

    override fun getItemCount(): Int {
        return article.size
    }

    inner class ItemViewHolder(binding: ItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
//        fun bind(article: ArticlesItem, onItemClick: (ArticlesItem) -> Unit) {
//            binding.article = article
//            binding.executePendingBindings()
//            binding.root.setOnClickListener { onItemClick(article) }
//        }
        val listLayoutBinding: ItemViewBinding = binding
    }


//    class ItemDiffCallback : DiffUtil.ItemCallback<ArticlesItem>() {
//        override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean = oldItem.url == newItem.url
//        override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean = oldItem == newItem
//    }
}