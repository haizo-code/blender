package com.haizo.sample.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.haizo.sample.util.GlideImageUtils

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    GlideImageUtils.setImage(view.context, url, imageView = view)
}

@BindingAdapter("circleImageUrl")
fun loadCircleImage(view: ImageView, url: String?) {
    GlideImageUtils.setCircleImage(view.context, url, imageView = view)
}