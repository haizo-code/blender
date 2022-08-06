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

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.haizo.generaladapter.interfaces.LoadMoreListener
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.LoadingListItem

internal abstract class BaseLoadMoreHelper {
    private lateinit var mRecyclerView: RecyclerView
    protected var mItems: MutableList<ListItem> = ArrayList()
    protected var loadMoreListener: LoadMoreListener? = null
    protected var loadingListItem: ListItem = LoadingObj()
    private var pageSize: Int = 10
    var isLoadMoreEnabled: Boolean = true
    var currentPage = 1
    var nextPageUrl: String? = null

    /**
     * Setup the loadMore behavior
     * @param [recyclerView]
     * @param [items]
     * @param [loadMoreListener]
     * @param [autoShowLoadingItem]
     * @param [loadingThreshold]: on index(n) from the end of the list, trigger the loadMore
     * @param [pageSize]: page size
     */
    fun setupLoadMore(
        recyclerView: RecyclerView,
        items: MutableList<ListItem>,
        loadMoreListener: LoadMoreListener,
        autoShowLoadingItem: Boolean,
        loadingThreshold: Int,
        pageSize: Int,
    ) {
        this.mRecyclerView = recyclerView
        this.loadMoreListener = loadMoreListener
        this.mItems = items
        this.pageSize = pageSize
        if (recyclerView.layoutManager is LinearLayoutManager) {
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    // To check if at the bottom of recycler view
                    if (!isLoadMoreEnabled) return
                    if (items.size < pageSize) return
                    if (linearLayoutManager!!.findLastCompletelyVisibleItemPosition() >= items.size - loadingThreshold) {
                        if (isLoadingItemAdded()) return
                        if (isSpammingCalls()) return
                        if (!isHasNextPage()) return
                        if (autoShowLoadingItem) addLoadMoreView()
                        loadMoreListener.onLoadMore(++currentPage, nextPageUrl)
                    }
                }
            })
        }
    }

    fun setLoadingListItem(loadingListItem: LoadingListItem?) {
        this.loadingListItem = loadingListItem ?: LoadingObj()
    }

    fun isLoadingItemAdded(): Boolean {
        return mItems.isNotEmpty() && mItems.last() is LoadingListItem
    }

    fun isHasNextPage(): Boolean {
        return mItems.size >= currentPage * pageSize
    }

    abstract fun addLoadMoreView()
    abstract fun removeLoadMoreIfExists()

    fun resetPage() {
        currentPage = 1
    }

    private var lastTriggerTime: Long = 0
    private fun isSpammingCalls(): Boolean {
        val newTriggerTime = System.currentTimeMillis()
        val isBelowInterval = System.currentTimeMillis() - lastTriggerTime < 500
        return isBelowInterval.also { lastTriggerTime = newTriggerTime }
    }
}