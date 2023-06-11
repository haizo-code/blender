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
package com.haizo.generaladapter.loadmore.legacyadapter

import androidx.recyclerview.widget.RecyclerView
import com.haizo.generaladapter.interfaces.LoadMoreListener
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder

/**
 * This is a abstract class for the RecyclerView Adapter that will handle the LoadMore behavior
 */
abstract class LoadMoreAdapter internal constructor() : RecyclerView.Adapter<BaseBindingViewHolder<ListItem>>() {

    private val mLoadMoreHelper: LoadMoreHelper by lazy { LoadMoreHelper(this) }

    /**
     *  adapter items: to be overridden and initialized in subClasses
     */
    protected abstract val items: MutableList<ListItem>

    /**
     * Used recyclerview in the current adapter
     * Value is auto @see onAttachedToRecyclerView()
     */
    protected lateinit var recyclerView: RecyclerView

    /**
     * @param autoShowLoadingItem: True to show the loading indicator when triggering the loadMore for next page
     * @param [loadingThreshold]: on index(n) from the end of the list, trigger the loadMore
     * @param loadMoreListener: load more listener callbacks
     */
    @JvmOverloads
    fun setupLoadMore(
        autoShowLoadingItem: Boolean = true,
        loadingThreshold: Int = 3,
        loadMoreListener: LoadMoreListener,
    ) {
        mLoadMoreHelper.setupLoadMore(
            recyclerView = recyclerView,
            items = items,
            loadMoreListener = loadMoreListener,
            autoShowLoadingItem = autoShowLoadingItem,
            loadingThreshold = loadingThreshold,
        )
    }

    /**
     * Use this method when you add the new loaded items from LoadMore
     * @param [list]: new items to add on the main list
     */
    @JvmOverloads
    fun addMoreItems(list: Collection<ListItem>, nextPagePayload: String? = null) {
        mLoadMoreHelper.addMoreItems(list, nextPagePayload)
    }

    /**
     * @param [nextPagePayload]: could be next page (url/offset/cursor)
     */
    fun setNextPagePayload(nextPagePayload: String?) {
        mLoadMoreHelper.nextPagePayload = nextPagePayload
    }

    /**
     * Reset the current page number to Page = 1
     */
    fun resetPageNumber() {
        mLoadMoreHelper.resetPage()
    }

    /**
     * Manual setting the value of the current page number
     * @param pageNumber
     */
    fun setCurrentPageNumber(pageNumber: Int) {
        mLoadMoreHelper.currentPage = pageNumber
    }

    /**
     * Adds a loading item in the list
     */
    fun addLoadingItem(commitCallback: Runnable? = null) {
        mLoadMoreHelper.addLoadMoreView(commitCallback)
    }

    /**
     * @return true if the loading is in progress
     */
    fun isLoading(): Boolean = mLoadMoreHelper.isLoadingInProgress

    /**
     * Remove the loading item from the list (if exists)
     */
    fun removeLoadingItem(commitCallback: Runnable? = null) {
        mLoadMoreHelper.removeLoadMoreIfExists(commitCallback)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }
}