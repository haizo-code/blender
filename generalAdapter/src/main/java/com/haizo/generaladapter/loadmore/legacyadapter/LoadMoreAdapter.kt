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
import com.haizo.generaladapter.LoadMoreNotInitialized
import com.haizo.generaladapter.interfaces.LoadMoreListener
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder

/**
 * This is a abstract class for the RecyclerView Adapter that will handle the LoadMore behavior
 */
abstract class LoadMoreAdapter : RecyclerView.Adapter<BaseBindingViewHolder<ListItem>>() {

    private var mLoadMoreHelper: LoadMoreHelper? = null

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
     * @param pageSize: the page size for the list, this is used to know when to trigger the next page
     * @param loadingThreshold: when to call the next page (ex. 3: when reaching the 7th item ([pageSize] - [loadingThreshold]) then call the next page)
     * @param loadMoreListener: load more listener callbacks
     */
    @JvmOverloads
    fun setupLoadMore(
        autoShowLoadingItem: Boolean = true,
        pageSize: Int = 0,
        loadingThreshold: Int = 3,
        loadMoreListener: LoadMoreListener,
    ) {
        if (mLoadMoreHelper == null) {
            // prevent the reInitialization of the helper to avoid losing the current page number
            mLoadMoreHelper = LoadMoreHelper(this)
        }
        mLoadMoreHelper?.setupLoadMore(
            recyclerView = recyclerView,
            items = items,
            loadMoreListener = loadMoreListener,
            autoShowLoadingItem = autoShowLoadingItem,
            loadingThreshold = loadingThreshold,
            pageSize = pageSize
        )
    }

    /**
     * Use this method when you add the new loaded items from LoadMore
     * @param [list]: new items to add on the main list
     */
    @JvmOverloads
    fun addMoreItems(list: Collection<ListItem>, nextPageUrl: String? = null) {
        mLoadMoreHelper?.addMoreItems(list, nextPageUrl) ?: kotlin.run { throw LoadMoreNotInitialized() }
    }

    fun setNextPageUrl(nextPageUrl: String?) {
        mLoadMoreHelper?.nextPageUrl = nextPageUrl
    }

    /**
     * Use this method to control enabling/disabling the load-more
     */
    fun setLoadMoreEnabled(isEnabled: Boolean) {
        mLoadMoreHelper?.let { it.isLoadMoreEnabled = isEnabled } ?: kotlin.run { throw LoadMoreNotInitialized() }
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
        mLoadMoreHelper?.let { it.currentPage = pageNumber } ?: kotlin.run { throw LoadMoreNotInitialized() }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }
}