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

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.util.LayoutDirection
import android.view.View
import androidx.annotation.DimenRes
import androidx.core.text.layoutDirection
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

/**
 * Create by Farouq Afghani
 * Item decoration for each item in the list for the recyclerview
 */
class ItemPaddingDecoration : RecyclerView.ItemDecoration {

    /**Offsets*/
    private var mTopOffset: Int = 0
    private var mBottomOffset: Int = 0
    private var mStartOffset: Int = 0
    private var mEndOffset: Int = 0

    /**The Index to start adding the padding from*/
    private var mStartingIndex: Int = 0

    /**Layout Direction*/
    private val isLTR: Boolean get() = Locale.getDefault().layoutDirection == LayoutDirection.LTR

    /**
     * This Method adds margin for each item in the list
     * @param [itemOffset] offset from all sides (start-end-top-bottom) for the item
     */
    constructor(context: Context, @DimenRes itemOffset: Int, startingIndex: Int = 0) {
        mTopOffset = context.resources.getDimensionPixelSize(itemOffset)
        mBottomOffset = mTopOffset
        mStartOffset = mTopOffset
        mEndOffset = mTopOffset
        mStartingIndex = startingIndex
    }

    /**
     * This Method adds margin for each item in the list
     * @param [itemOffset] offset from all sides (start-end-top-bottom) for the item
     */
    constructor(itemOffset: Int, startingIndex: Int = 0, convertDpToPx: Boolean = true) {
        mTopOffset = itemOffset.let { if (convertDpToPx) dpToPx(it) else it }
        mBottomOffset = mTopOffset
        mStartOffset = mTopOffset
        mEndOffset = mTopOffset
        mStartingIndex = startingIndex
    }

    /**
     * This Method adds margin for each item in the list
     * @param [startEndOffset] offset from (Start and End) for the item
     * @param [topBottomOffset] offset from (Top and Bottom) for the item
     */
    constructor(
        context: Context,
        @DimenRes startEndOffset: Int,
        @DimenRes topBottomOffset: Int,
        startingIndex: Int = 0
    ) {
        this.mStartOffset = context.resources.getDimensionPixelSize(startEndOffset)
        this.mEndOffset = startEndOffset
        this.mTopOffset = context.resources.getDimensionPixelSize(topBottomOffset)
        this.mBottomOffset = mTopOffset
        mStartingIndex = startingIndex
    }

    /**
     * This Method adds margin for each item in the list
     * @param [startEndOffset] offset from (Start and End) for the item
     * @param [topBottomOffset] offset from (Top and Bottom) for the item
     * @param [convertDpToPx]: True -> will convert the inputs from Px to Dp
     */
    constructor(
        startEndOffset: Int,
        topBottomOffset: Int,
        startingIndex: Int = 0,
        convertDpToPx: Boolean = true
    ) {
        this.mStartOffset = startEndOffset.let { if (convertDpToPx) dpToPx(it) else it }
        this.mEndOffset = this.mStartOffset
        this.mTopOffset = topBottomOffset.let { if (convertDpToPx) dpToPx(it) else it }
        this.mBottomOffset = this.mTopOffset
        this.mStartingIndex = startingIndex
    }

    /**
     * This Method adds margin for each item in the list
     * @param [start] offset from the start only for the item
     * @param [end] offset from the end only for the item
     * @param [top] offset from the top only for the item
     * @param [bottom] offset from the bottom only for the item
     */
    constructor(
        context: Context,
        @DimenRes start: Int = 0,
        @DimenRes end: Int = 0,
        @DimenRes top: Int = 0,
        @DimenRes bottom: Int = 0,
        startingIndex: Int = 0
    ) {
        this.mStartOffset = context.resources.getDimensionPixelSize(start)
        this.mEndOffset = context.resources.getDimensionPixelSize(end)
        this.mTopOffset = context.resources.getDimensionPixelSize(top)
        this.mBottomOffset = context.resources.getDimensionPixelSize(bottom)
        this.mStartingIndex = startingIndex
    }

    /**
     * This Method adds margin for each item in the list
     * @param [start] offset from the start only for the item
     * @param [end] offset from the end only for the item
     * @param [top] offset from the top only for the item
     * @param [bottom] offset from the bottom only for the item
     * @param [convertDpToPx]: True -> will convert the inputs from Px to Dp
     */
    constructor(
        start: Int = 0,
        end: Int = 0,
        top: Int = 0,
        bottom: Int = 0,
        startingIndex: Int = 0,
        convertDpToPx: Boolean = true
    ) {
        this.mStartOffset = start.let { if (convertDpToPx) dpToPx(it) else it }
        this.mEndOffset = end.let { if (convertDpToPx) dpToPx(it) else it }
        this.mTopOffset = top.let { if (convertDpToPx) dpToPx(it) else it }
        this.mBottomOffset = bottom.let { if (convertDpToPx) dpToPx(it) else it }
        this.mStartingIndex = startingIndex
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) >= mStartingIndex) {
            if (isLTR) {
                outRect.set(mStartOffset, mTopOffset, mEndOffset, mBottomOffset)
            } else {
                outRect.set(mEndOffset, mTopOffset, mStartOffset, mBottomOffset)
            }
        }
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }
}