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
package com.haizo.generaladapter.loadmore.listadapter

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.haizo.generaladapter.utils.LoadMoreNotInitialized
import com.haizo.generaladapter.interfaces.LoadMoreListener
import com.haizo.generaladapter.listadapter.DiffCallbacks
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.LoadingListItem
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder

/**
 * This is a abstract class for the RecyclerView Adapter that will handle the LoadMore behavior
 */
abstract class LoadMoreListAdapter internal constructor() :
    ListAdapter<ListItem, BaseBindingViewHolder<ListItem>>(DiffCallbacks.LIST_ITEM_COMPARATOR) {

    private var mLoadMoreListHelper: LoadMoreListHelper? = null

    /**
     * Used recyclerview in the current adapter
     * Value is auto @see onAttachedToRecyclerView()
     */
    protected lateinit var recyclerView: RecyclerView

    private val loadMoreList: MutableList<ListItem> = ArrayList()

    /**
     * @param loadMoreListener: load more listener callbacks
     * @param autoShowLoadingItem: True to show the loading indicator when triggering the loadMore for next page
     * @param pageSize: the page size for the list, this is used to know when to trigger the next page
     * @param loadingThreshold: when to call the next page (ex. 3: when reaching the 7th item ([pageSize] - [loadingThreshold]) then call the next page)
     */
    @JvmOverloads
    fun setupLoadMore(
        autoShowLoadingItem: Boolean = true,
        pageSize: Int = 10,
        loadingThreshold: Int = 3,
        loadMoreListener: LoadMoreListener,
    ) {
        if (mLoadMoreListHelper == null) {
            // prevent the reInitialization of the helper to avoid losing the current page number
            mLoadMoreListHelper = LoadMoreListHelper(this)
        }
        mLoadMoreListHelper?.setupLoadMore(
            recyclerView = recyclerView,
            items = loadMoreList,
            loadMoreListener = loadMoreListener,
            autoShowLoadingItem = autoShowLoadingItem,
            loadingThreshold = loadingThreshold,
            pageSize = pageSize
        )
    }

    /**
     * Use this method to control enabling/disabling the load-more
     */
    fun setLoadMoreEnabled(isEnabled: Boolean) {
        mLoadMoreListHelper?.let { it.isLoadMoreEnabled = isEnabled } ?: kotlin.run { throw LoadMoreNotInitialized() }
    }

    /**
     * Use this method to set your custom Loading indicator listItem
     * hint: it will be added at the end of the current list once the load-more logic started
     *       and will be removed once the loading is finished
     */
    fun setLoadingListItem(loadingListItem: LoadingListItem?) {
        mLoadMoreListHelper?.setLoadingListItem(loadingListItem)
    }

    /**
     * The added param here will be passed to the LoadMore callback when the onLoadMore method is triggered
     */
    fun setNextPageUrl(nextPageUrl: String?) {
        mLoadMoreListHelper?.nextPageUrl = nextPageUrl
    }

    /**
     * This method will reset the page number and update the current list with the new list
     * Use this method to submit the first page
     */
    @JvmOverloads
    fun submitListItems(list: List<ListItem>?, nextPageUrl: String? = null, commitCallback: Runnable? = null) {
        mLoadMoreListHelper?.let {
            it.nextPageUrl = nextPageUrl
            it.updateListItems(list)
        }
        super.submitList(list, commitCallback)
    }

    fun submitListItems(list: List<ListItem>?, commitCallback: Runnable? = null) {
        submitListItems(list, null, commitCallback)
    }

    /**
     * Use this method when you add the new loaded items from LoadMore
     * @param [list]: new items to add on the main list
     */
    @JvmOverloads
    fun submitMoreListItems(list: List<ListItem>, nextPageUrl: String? = null, commitCallback: Runnable? = null) {
        mLoadMoreListHelper?.let {
            it.nextPageUrl = nextPageUrl
            it.addMoreItems(list, commitCallback)
        } ?: kotlin.run { throw LoadMoreNotInitialized() }
    }

    fun submitMoreListItems(list: List<ListItem>, commitCallback: Runnable? = null) {
        submitMoreListItems(list, null, commitCallback)
    }

    /**
     * Manual setting the value of the current page number
     * @param pageNumber
     */
    fun setCurrentPageNumber(pageNumber: Int) {
        mLoadMoreListHelper?.let { it.currentPage = pageNumber } ?: kotlin.run { throw LoadMoreNotInitialized() }
    }

    /**
     * @return current loading page
     */
    fun getCurrentPageNumber(): Int {
        return mLoadMoreListHelper?.currentPage ?: kotlin.run { throw LoadMoreNotInitialized() }
    }

    /**
     * Use this method to clear the current 'loadMoreHelper' instance
     */
    fun clearLoadMoreInstance() {
        mLoadMoreListHelper = null
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    @Suppress("RedundantOverride")
    override fun getCurrentList(): MutableList<ListItem> {
        return super.getCurrentList()
    }
}