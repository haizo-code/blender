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

    /**
     * @return True if the loadMore is enabled for the current adapter
     */
    var isLoadMoreEnabled: Boolean
        get() = mLoadMoreListHelper?.isLoadMoreEnabled ?: false
        set(isEnabled) {
            mLoadMoreListHelper?.isLoadMoreEnabled = isEnabled
        }

    val loadMoreList: MutableList<ListItem> = ArrayList()

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

    /**
     * Use this method when you add the new loaded items from LoadMore
     * @param [list]: new items to add on the main list
     */
    fun submitMoreList(page: Int, list: Collection<ListItem>, commitCallback: Runnable? = null) {
        if (mLoadMoreListHelper == null) throw NullPointerException(ERROR_NOT_INITIALIZED)
        if (page == 1) {
            mLoadMoreListHelper!!.resetPage()
            loadMoreList.clear()
            loadMoreList.addAll(list)
            submitList(loadMoreList.toList(), commitCallback)
        } else {
            mLoadMoreListHelper!!.addMoreItems(list)
        }
    }

    /**
     * Manual setting the value of the current page number
     * @param pageNumber
     */
    fun setCurrentPageNumber(pageNumber: Int) {
        if (mLoadMoreListHelper == null) throw NullPointerException(ERROR_NOT_INITIALIZED)
        mLoadMoreListHelper!!.setCurrentPage(pageNumber)
    }

    fun getCurrentPageNumber(): Int {
        if (mLoadMoreListHelper == null) throw NullPointerException(ERROR_NOT_INITIALIZED)
        return mLoadMoreListHelper!!.getCurrentPage()
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