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

    private val mLoadMoreListHelper: LoadMoreListHelper by lazy {
        LoadMoreListHelper(this)
    }

    /**
     * Used recyclerview in the current adapter
     * Value is auto @see onAttachedToRecyclerView()
     */
    protected lateinit var recyclerView: RecyclerView

    /**
     * @param loadMoreListener: load more listener callbacks
     * @param autoShowLoadingItem: True to show the loading indicator when triggering the loadMore for next page
     * @param [loadingThreshold]: on index(n) from the end of the list, trigger the loadMore
     */
    @JvmOverloads
    fun setupLoadMore(
        autoShowLoadingItem: Boolean = true,
        loadingThreshold: Int = 3,
        loadMoreListener: LoadMoreListener,
    ) {
        mLoadMoreListHelper.setupLoadMore(
            recyclerView = recyclerView,
            items = currentList.toMutableList(),
            loadMoreListener = loadMoreListener,
            autoShowLoadingItem = autoShowLoadingItem,
            loadingThreshold = loadingThreshold,
        )
    }

    /**
     * Use this method to set your custom Loading indicator listItem
     * hint: it will be added at the end of the current list once the load-more logic started
     *       and will be removed once the loading is finished
     */
    fun setLoadingListItem(loadingListItem: LoadingListItem?) {
        mLoadMoreListHelper.setLoadingListItem(loadingListItem)
    }

    /**
     * Adds a loading item in the list
     */
    @JvmOverloads
    fun addLoadingItem(commitCallback: Runnable? = null) {
        mLoadMoreListHelper.addLoadMoreView(commitCallback)
    }

    /**
     * Remove the loading item from the list (if exists)
     */
    @JvmOverloads
    fun removeLoadingItem(commitCallback: Runnable? = null) {
        mLoadMoreListHelper.removeLoadMoreIfExists(commitCallback)
    }

    /**
     * The added param here will be passed to the LoadMore callback when the onLoadMore method is triggered
     */
    fun setNextPagePayload(nextPagePayload: String?) {
        mLoadMoreListHelper.nextPagePayload = nextPagePayload
    }

    /**
     * This method will reset the page number and update the current list with the new list
     * Use this method to submit the first page
     */
    @JvmOverloads
    fun submitListItems(list: List<ListItem>?, nextPagePayload: String? = null, commitCallback: Runnable? = null) {
        mLoadMoreListHelper.let {
            it.nextPagePayload = nextPagePayload
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
    fun submitMoreListItems(list: List<ListItem>, nextPagePayload: String? = null, commitCallback: Runnable? = null) {
        mLoadMoreListHelper.let {
            it.nextPagePayload = nextPagePayload
            it.addMoreItems(list, commitCallback)
        }
    }

    fun submitMoreListItems(list: List<ListItem>, commitCallback: Runnable? = null) {
        submitMoreListItems(list, null, commitCallback)
    }

    /**
     * Manual setting the value of the current page number
     * @param pageNumber
     */
    @Suppress("MemberVisibilityCanBePrivate")
    fun setCurrentPageNumber(pageNumber: Int) {
        mLoadMoreListHelper.currentPage = pageNumber
    }

    /**
     * @return current loading page
     */
    fun getCurrentPageNumber(): Int {
        return mLoadMoreListHelper.currentPage
    }

    /**
     * Reset current page number
     */
    fun resetPageNumber() {
        setCurrentPageNumber(0)
    }

    /**
     * @return true if the loading is in progress
     */
    fun isLoading(): Boolean = mLoadMoreListHelper.isLoadingInProgress

    /**
     * @return true if the list is empty, otherwise false
     */
    fun isEmpty() = currentList.isEmpty()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    final override fun submitList(list: List<ListItem>?) {
        submitList(list, null)
    }

    override fun submitList(list: List<ListItem>?, commitCallback: Runnable?) {
        mLoadMoreListHelper.updateMainList(list)
        super.submitList(list, commitCallback)
    }
}