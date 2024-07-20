package com.news.newsapplication.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.news.newsapplication.R

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .into(imageView)
    } else {
        imageView.setImageResource(R.drawable.place_holder) // Use a placeholder image if URL is null or empty
    }
}