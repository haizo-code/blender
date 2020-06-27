package com.haizo.generaladapter.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

/**
 * Create by Farouq Afghani at 12-2-2019
 * Item decoration for each item in the list for the recyclerview
 */
class ItemPaddingDecoration : RecyclerView.ItemDecoration {

    private var mTopOffset: Int = 0
    private var mBottomOffset: Int = 0
    private var mStartOffset: Int = 0
    private var mEndOffset: Int = 0
    private var startingIndex: Int = 0

    /**
     * This Method adds margin for each item in the list
     * @param [itemOffset] offset from all sides (start-end-top-bottom) for the item
     */
    constructor(itemOffset: Int) {
        mTopOffset = dpToPx(itemOffset)
        mBottomOffset = mTopOffset
        mStartOffset = mTopOffset
        mEndOffset = mTopOffset
    }

    /**
     * This Method adds margin for each item in the list
     * @param [startEndOffset] offset from (Start and End) for the item
     * @param [topBottomOffset] offset from (Top and Bottom) for the item
     */
    constructor(context: Context, @DimenRes startEndOffset: Int, @DimenRes topBottomOffset: Int) {
        this.mStartOffset = context.resources.getDimensionPixelSize(startEndOffset)
        mEndOffset = startEndOffset
        this.mTopOffset = context.resources.getDimensionPixelSize(topBottomOffset)
        mBottomOffset = mTopOffset
    }

    /**
     * This Method adds margin for each item in the list
     * @param [startEndOffset] offset from (Start and End) for the item
     * @param [topBottomOffset] offset from (Top and Bottom) for the item
     */
    constructor(startEndOffset: Int, topBottomOffset: Int, startingIndex: Int = 0) {
        this.mStartOffset = dpToPx(startEndOffset)
        this.mEndOffset = dpToPx(startEndOffset)
        this.mTopOffset = dpToPx(topBottomOffset)
        this.mBottomOffset = dpToPx(topBottomOffset)
        this.startingIndex = startingIndex
    }

    /**
     * This Method adds margin for each item in the list
     * @param [start] offset from the start only for the item
     * @param [end] offset from the end only for the item
     * @param [top] offset from the top only for the item
     * @param [bottom] offset from the bottom only for the item
     */
    constructor(start: Int = 0, end: Int = 0, top: Int = 0, bottom: Int = 0, startingIndex: Int = 0) {
        this.mStartOffset = dpToPx(start)
        this.mEndOffset = dpToPx(end)
        this.mTopOffset = dpToPx(top)
        this.mBottomOffset = dpToPx(bottom)
        this.startingIndex = startingIndex
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) >= startingIndex) {
            outRect.set(mStartOffset, mTopOffset, mEndOffset, mBottomOffset)
        }
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }
}