package com.haizo.generaladapter.interfaces

/**
 * This interface is used to override the RecyclerView.Adapter functions as the already defined methods below
 */
interface RecyclerViewAdapterCallback {
    fun onViewAttachedToWindow() {}
    fun onViewDetachedFromWindow() {}
    fun onViewRecycled() {}
}