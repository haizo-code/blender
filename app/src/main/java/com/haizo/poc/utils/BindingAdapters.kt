package com.haizo.poc.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    GlideImageUtils.setImage(view.context, url, imageView = view)
}

@BindingAdapter("circleImageUrl")
fun loadCircleImage(view: ImageView, url: String?) {
    GlideImageUtils.setCircleImage(view.context, url, imageView = view)
}