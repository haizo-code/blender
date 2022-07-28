package com.haizo.sample.util

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.haizo.sample.R

class Utils {

    fun getCircularProgressDrawable(context: Context): CircularProgressDrawable {
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.setColorSchemeColors(
            ContextCompat.getColor(context, R.color.colorAccent),
            ContextCompat.getColor(context, R.color.colorPrimary),
            ContextCompat.getColor(context, R.color.colorPrimaryDark)
        )
        circularProgressDrawable.arrowEnabled = true
        circularProgressDrawable.arrowScale = 5f
        circularProgressDrawable.setArrowDimensions(3f, 3f)
        circularProgressDrawable.start()
        return circularProgressDrawable
    }

}