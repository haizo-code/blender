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

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.haizo.generaladapter.LoadMoreNotInitialized
import com.haizo.generaladapter.interfaces.LoadMoreListener
import com.haizo.generaladapter.listadapter.DiffCallbacks
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder

/**
 * This is a abstract class for the RecyclerView Adapter that will handle the LoadMore behavior
 */
abstract class LoadMoreListAdapter :
    ListAdapter<ListItem, BaseBindingViewHolder<ListItem>>(DiffCallbacks.LIST_ITEM_COMPARATOR) {

    private var mLoadMoreListHelper: LoadMoreListHelper? = null

    /**
     * Used recyclerview in the current adapter
     * Value is auto @see onAttachedToRecyclerView()
     */
    protected lateinit var recyclerView: RecyclerView

    private val loadMoreList: MutableList<ListItem> = ArrayList()

    /**
     * @return True if the loadMore is enabled for the current adapter
     */
    var isLoadMoreEnabled: Boolean
        get() = mLoadMoreListHelper?.isLoadMoreEnabled ?: false
        set(isEnabled) {
            mLoadMoreListHelper?.isLoadMoreEnabled = isEnabled
        }

    /**
     * @param loadMoreListener: load more listener callbacks
     * @param autoShowLoadingItem: True to show the loading indicator when triggering the loadMore for next page
     * @param pageSize: the page size for the list, this is used to know when to trigger the next page
     * @param loadingThreshold: when to call the next page (ex. 3: when reaching the 7th item ([pageSize] - [loadingThreshold]) then call the next page)
     */
    fun setupLoadMore(
        loadMoreListener: LoadMoreListener,
        autoShowLoadingItem: Boolean = true,
        pageSize: Int = 10,
        loadingThreshold: Int = 3
    ) {
        if (mLoadMoreListHelper == null) {
            // prevent the reInitialization of the helper to avoid losing the current page number
            mLoadMoreListHelper = LoadMoreListHelper(this, pageSize, loadingThreshold)
        }
        mLoadMoreListHelper?.setupLoadMore(recyclerView, loadMoreList, loadMoreListener, autoShowLoadingItem)
    }

    /**
     * If you need to reinitialize the loadMoreHelper instance, then use this method
     */
    fun clearLoadMoreInstance() {
        mLoadMoreListHelper = null
    }

    @Deprecated("Use the new methods for better experience",
        ReplaceWith("SubmitListItems() and submitMoreListItems() "))
    fun submitMoreList(page: Int, list: Collection<ListItem>, commitCallback: Runnable? = null) {
        if (page == 1) {
            submitListItems(list.toList(), commitCallback)
        } else {
            submitMoreListItems(list.toList(), commitCallback)
        }
    }

    /**
     * This method will reset the page number and update the current list with the new list
     * Use this method to submit the first page
     */
    fun submitListItems(list: List<ListItem>?, commitCallback: Runnable? = null) {
        mLoadMoreListHelper?.updateListItems(list)
        super.submitList(list, commitCallback)
    }

    /**
     * Use this method when you add the new loaded items from LoadMore
     * @param [list]: new items to add on the main list
     */
    fun submitMoreListItems(list: List<ListItem>, commitCallback: Runnable? = null) {
        mLoadMoreListHelper?.addMoreItems(list, commitCallback) ?: kotlin.run { throw LoadMoreNotInitialized() }
    }

    /**
     * Manual setting the value of the current page number
     * @param pageNumber
     */
    fun setCurrentPageNumber(pageNumber: Int) {
        mLoadMoreListHelper?.setCurrentPage(pageNumber) ?: kotlin.run { throw LoadMoreNotInitialized() }
    }

    fun getCurrentPageNumber(): Int {
        return mLoadMoreListHelper?.getCurrentPage() ?: kotlin.run { throw LoadMoreNotInitialized() }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }
}