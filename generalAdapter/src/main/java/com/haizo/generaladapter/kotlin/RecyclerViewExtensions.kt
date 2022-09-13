package com.haizo.generaladapter.kotlin

import android.util.LayoutDirection
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.flexbox.FlexboxLayoutManager

fun RecyclerView.setupVertical() = apply {
    setupItemAnimatorWithNoChangeAnimation()
    layoutManager = LinearLayoutManager(context)
}

fun RecyclerView.setupHorizontal() = apply {
    setupItemAnimatorWithNoChangeAnimation()
    layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
}

fun RecyclerView.setupGrid(spanCount: Int) = apply {
    setupItemAnimatorWithNoChangeAnimation()
    layoutManager = GridLayoutManager(context, spanCount)
}

fun RecyclerView.setupItemAnimatorWithNoChangeAnimation() = apply {
    itemAnimator = DefaultItemAnimator().apply { supportsChangeAnimations = false }
}

/**
 * @return The adapter position of the first visible item or {@link RecyclerView#NO_POSITION} if
 * there aren't any visible items.
 */
fun RecyclerView.findFirstVisibleItemPosition(): Int {
    (layoutManager as? StaggeredGridLayoutManager)?.let {
        return it.findFirstVisibleItemPositions(null).let { into ->
            if (into.isNotEmpty()) {
                into[/*spanIndex=*/if (it.layoutDirection == LayoutDirection.RTL) into.size - 1 else 0]
            } else {
                RecyclerView.NO_POSITION
            }
        }
    }
    (layoutManager as? FlexboxLayoutManager)?.let {
        return it.findFirstVisibleItemPosition()
    }
    // The below check include the GridLayoutManager
    (layoutManager as? LinearLayoutManager)?.let {
        return it.findFirstVisibleItemPosition()
    }
    return RecyclerView.NO_POSITION
}

/**
 *  @return The adapter position of the first fully visible item or
 * {@link RecyclerView#NO_POSITION} if there aren't any visible items.
 */
fun RecyclerView.findFirstCompletelyVisibleItemPosition(): Int {
    (layoutManager as? StaggeredGridLayoutManager)?.let {
        return it.findFirstCompletelyVisibleItemPositions(null).let { into ->
            if (into.isNotEmpty()) {
                into[/*spanIndex=*/if (it.layoutDirection == LayoutDirection.RTL) into.size - 1 else 0]
            } else {
                RecyclerView.NO_POSITION
            }
        }
    }
    (layoutManager as? FlexboxLayoutManager)?.let {
        return it.findFirstCompletelyVisibleItemPosition()
    }
    // The below check include the GridLayoutManager
    (layoutManager as? LinearLayoutManager)?.let {
        return it.findFirstCompletelyVisibleItemPosition()
    }
    return RecyclerView.NO_POSITION
}

/**
 * @return The adapter position of the last visible view or {@link RecyclerView#NO_POSITION} if
 * there aren't any visible items.
 */
fun RecyclerView.findLastVisibleItemPosition(): Int {
    (layoutManager as? StaggeredGridLayoutManager)?.let {
        return it.findLastVisibleItemPositions(null).let { into ->
            if (into.isNotEmpty()) {
                into[/*spanIndex=*/if (it.layoutDirection == LayoutDirection.RTL) 0 else into.size - 1]
            } else {
                RecyclerView.NO_POSITION
            }
        }
    }
    (layoutManager as? FlexboxLayoutManager)?.let {
        return it.findLastVisibleItemPosition()
    }
    // The below check include the GridLayoutManager
    (layoutManager as? LinearLayoutManager)?.let {
        return it.findLastVisibleItemPosition()
    }
    return RecyclerView.NO_POSITION
}

/**
 * @return The adapter position of the last fully visible view or
 * {@link RecyclerView#NO_POSITION} if there aren't any visible items.
 */
fun RecyclerView.findLastCompletelyVisibleItemPosition(): Int {
    (layoutManager as? StaggeredGridLayoutManager)?.let {
        return it.findLastCompletelyVisibleItemPositions(null).let { into ->
            if (into.isNotEmpty()) {
                into[/*spanIndex=*/if (it.layoutDirection == LayoutDirection.RTL) 0 else into.size - 1]
            } else {
                RecyclerView.NO_POSITION
            }
        }
    }
    (layoutManager as? FlexboxLayoutManager)?.let {
        return it.findLastCompletelyVisibleItemPosition()
    }
    // The below check include the GridLayoutManager
    (layoutManager as? LinearLayoutManager)?.let {
        return it.findLastCompletelyVisibleItemPosition()
    }
    return RecyclerView.NO_POSITION
}