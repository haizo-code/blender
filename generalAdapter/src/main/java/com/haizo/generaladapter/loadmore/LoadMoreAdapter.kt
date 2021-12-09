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
package com.haizo.generaladapter.loadmore

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.haizo.generaladapter.interfaces.LoadMoreListener

/**
 * This is a abstract class for the RecyclerView Adapter that will handle the LoadMore behavior
 */
abstract class LoadMoreAdapter<T, VH : RecyclerView.ViewHolder?> : RecyclerView.Adapter<VH>() {

    private var mLoadMoreHelper: LoadMoreHelper<T>? = null

    /**
     *  adapter items: to be overridden and initialized in subClasses
     */
    protected abstract val items: MutableList<T>

    /**
     * Used recyclerview in the current adapter
     * Value is auto @see onAttachedToRecyclerView()
     */
    protected var recyclerView: RecyclerView? = null

    /**
     * @return True if the loadMore is enabled for the current adapter
     */
    var isLoadMoreEnabled: Boolean
        get() = mLoadMoreHelper?.isLoadMoreEnabled ?: false
        set(isEnabled) {
            mLoadMoreHelper?.isLoadMoreEnabled = isEnabled
        }

    /**
     * @param loadMoreListener: load more listener callbacks
     * @param autoShowLoadingItem: True to show the loading indicator when triggering the loadMore for next page
     * @param pageSize: the page size for the list, this is used to know when to trigger the next page
     * @param loadingThreshold: when to call the next page (ex. 3: when reaching the 7th item ([pageSize] - [loadingThreshold]) then call the next page)
     */
    fun setupLoadMore(loadMoreListener: LoadMoreListener?, autoShowLoadingItem: Boolean = true, pageSize: Int = 10,
        loadingThreshold: Int = 3) {
        if (recyclerView == null || loadMoreListener == null) return
        mLoadMoreHelper = LoadMoreHelper(adapter = this, pageSize = pageSize, loadingThreshold = loadingThreshold)
        mLoadMoreHelper?.setupLoadMore(recyclerView!!, items, loadMoreListener, autoShowLoadingItem)
    }

    /**
     * Use this method when you add the new loaded items from LoadMore
     * @param [list]: new items to add on the main list
     */
    fun addMoreItems(list: Collection<T>?) {
        if (mLoadMoreHelper == null) Log.d("LoadMoreHelper", ERROR_NOT_INITIALIZED)
        mLoadMoreHelper?.addMoreItems(list)
    }

    /**
     * Remove the loading indicator if exists and triggers the callback for @see [LoadMoreListener.onLoadMoreFinished]
     */
    fun removeLoadMoreIfExists() {
        mLoadMoreHelper?.removeLoadMoreIfExists()
    }

    /**
     * Reset the current page number to Page = 1
     */
    fun resetPageNumber() {
        mLoadMoreHelper?.resetPage()
    }

    /**
     * Manual setting the value of the current page number
     * @param pageNumber
     */
    fun setCurrentPageNumber(pageNumber: Int) {
        if (mLoadMoreHelper == null) Log.d("LoadMoreHelper", ERROR_NOT_INITIALIZED)
        mLoadMoreHelper?.setCurrentPage(pageNumber)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    companion object {
        private const val ERROR_NOT_INITIALIZED =
            "You forgot to setup the LoadMore helper, Please set it up and try again"
    }
}