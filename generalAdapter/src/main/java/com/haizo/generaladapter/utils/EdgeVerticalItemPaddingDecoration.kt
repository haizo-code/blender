/*
 * Copyright 2020 Farouq Afghani
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.haizo.generaladapter.utils

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Create by Farouq Afghani at 12-2-2019
 * This is a helper class to add padding for the edges ONLY, which means the padding will be added
 * for the (first|last) items only
 */
class EdgeVerticalItemPaddingDecoration constructor(
    private val paddingTop: Int = 0,
    private val paddingBottom: Int = 0,
    private val convertDpToPx: Boolean = true
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        recyclerView: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, recyclerView, state)

        // Apply offset only to first item
        if (recyclerView.getChildAdapterPosition(view) == 0) {
            outRect.top += paddingTop.let { if (convertDpToPx) dpToPx(it) else it }
        } else if (recyclerView.getChildAdapterPosition(view) == (recyclerView.layoutManager as LinearLayoutManager).itemCount - 1) {
            outRect.bottom += paddingBottom.let { if (convertDpToPx) dpToPx(it) else it }
        }
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }
}