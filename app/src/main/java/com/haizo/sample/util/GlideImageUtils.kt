package com.haizo.sample.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.haizo.sample.R

/**
 * Created by Farouq Afghani on 2019-03-19.
 */
object GlideImageUtils {

    fun setImage(context: Context?, imageToLoad: Any?, fastLoadUrl: Any? = null, imageView: ImageView?) {
        if (context == null || imageView == null) return

        val requestOption = RequestOptions()
            .fitCenter()
            .placeholder(Utils().getCircularProgressDrawable(context))

        val glideLoader = Glide
            .with(context.applicationContext)
            .load(imageToLoad ?: "")
            .error(R.drawable.ic_baseline_error_outline_24)
            .apply(requestOption)

        if (fastLoadUrl != null) {
            glideLoader.thumbnail(
                Glide.with(context.applicationContext)
                    .load(fastLoadUrl)
                    .apply(requestOption)
            ).into(imageView)
            return
        }
        glideLoader.into(imageView)
    }

    fun setCircleImage(context: Context?, imageToLoad: Any?, imageView: ImageView?) {
        if (context == null || imageView == null) return

        val requestOption = RequestOptions()
            .circleCrop()
            .placeholder(Utils().getCircularProgressDrawable(context))

        Glide
            .with(context.applicationContext)
            .asBitmap()
            .load(imageToLoad)
            .error(R.drawable.ic_baseline_error_outline_24)
            .apply(requestOption)
            .into(imageView)
    }
}

val sampleBackgrounds = listOf(
    "https://irishfree.com/wp-content/uploads/2020/04/doctorstrange_01-1920x1080_copy.jpg",
    "https://i.ytimg.com/vi/D5nYYkTHCCg/maxresdefault.jpg",
    "https://cdn4.vectorstock.com/i/1000x1000/24/13/brick-wall-room-background-neon-light-vector-27852413.jpg",
    "https://www.freevector.com/uploads/vector/preview/30374/Colorful-Plait-Background.jpg",
    "https://thumbs.dreamstime.com/b/hexagon-dark-blue-abstract-background-modern-149052337.jpg",
    "https://static.vecteezy.com/system/resources/previews/000/558/627/non_2x/vector-abstract-background-technology-network-design.jpg"
)