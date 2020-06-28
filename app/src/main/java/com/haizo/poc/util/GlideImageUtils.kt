package com.haizo.poc.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.haizo.poc.R

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
    "https://3pageplus.com/wp-content/uploads/2019/03/change-background-color-on-wix-1024x678.jpg",
    "https://www.freevector.com/uploads/vector/preview/30374/Colorful-Plait-Background.jpg",
    "https://images.pexels.com/photos/956981/milky-way-starry-sky-night-sky-star-956981.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
    "https://d1csarkz8obe9u.cloudfront.net/posterpreviews/summer-beach-zoom-virtual-background-video-design-template-c95b9c7d4c37ed391e8cbc1aeb7f1127_screen.jpg?ts=1589072498",
    "https://cdn4.vectorstock.com/i/1000x1000/24/13/brick-wall-room-background-neon-light-vector-27852413.jpg",
    "https://img.freepik.com/free-vector/abstract-colorful-flow-shapes-background_23-2148258092.jpg?size=626&ext=jpg",
    "https://thumbs.dreamstime.com/b/colorful-rainbow-holi-paint-color-powder-explosion-isolated-white-wide-panorama-background-colorful-rainbow-holi-paint-color-143749617.jpg",
    "https://i.ytimg.com/vi/D5nYYkTHCCg/maxresdefault.jpg",
    "https://cache.desktopnexus.com/thumbseg/374/374776-bigthumbnail.jpg",
    "https://irishfree.com/wp-content/uploads/2020/04/doctorstrange_01-1920x1080_copy.jpg",
    "https://cdn.dribbble.com/users/4250227/screenshots/10875160/mandala-12c.png",
    "https://d2gg9evh47fn9z.cloudfront.net/800px_COLOURBOX10557177.jpg"
)
