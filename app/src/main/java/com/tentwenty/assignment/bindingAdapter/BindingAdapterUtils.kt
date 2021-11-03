package com.tentwenty.assignment.bindingAdapter

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapterUtils {
    @JvmStatic
    @BindingAdapter("moviePoster")
    fun loadPoster(view: AppCompatImageView, imageUrl: String?) {
        val url = "https://image.tmdb.org/t/p/w500$imageUrl"
        Glide.with(view.context)
            .load(url)
            .into(view)
    }
}