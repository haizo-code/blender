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

import androidx.recyclerview.widget.RecyclerView
import com.haizo.generaladapter.interfaces.LoadMoreListener
import com.haizo.generaladapter.kotlin.findLastCompletelyVisibleItemPosition
import com.haizo.generaladapter.listitems.MockLoadingListItem
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.LoadingListItem

internal abstract class BaseLoadMoreHelper {
    protected var mItems: MutableList<ListItem> = ArrayList()
    protected var loadMoreListener: LoadMoreListener? = null
    protected var loadingListItem: ListItem = MockLoadingListItem()
    var isLoadingInProgress: Boolean = false
    private var mScrollListener: RecyclerView.OnScrollListener? = null
    var currentPage = 1
    var nextPagePayload: String? = null

    /**
     * Setup the loadMore behavior
     * @param [recyclerView]
     * @param [items]
     * @param [loadMoreListener]
     * @param [autoShowLoadingItem]
     * @param [loadingThreshold]: on index(n) from the end of the list, trigger the loadMore
     * <p> by override the [LoadMoreListener.isShouldTriggerLoadMore] method in the [LoadMoreListener] since the [LoadMoreListener.onLoadMore]
     * <p> will always be triggered since it satisfy the threshold logic
     */
    fun setupLoadMore(
        recyclerView: RecyclerView,
        items: MutableList<ListItem>,
        loadMoreListener: LoadMoreListener,
        autoShowLoadingItem: Boolean,
        loadingThreshold: Int,
    ) {
        this.loadMoreListener = loadMoreListener
        this.mItems = items
        mScrollListener?.let { recyclerView.removeOnScrollListener(it) }
        mScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (recyclerView.findLastCompletelyVisibleItemPosition() >= items.size - loadingThreshold) {
                    if (!loadMoreListener.isShouldTriggerLoadMore(currentPage + 1, nextPagePayload)) return
                    if (isLoadingInProgress) return
                    if (isSpammingCalls()) return
                    isLoadingInProgress = true
                    if (autoShowLoadingItem && !isLoadingItemAdded()) addLoadMoreView()
                    loadMoreListener.onLoadMore(++currentPage, nextPagePayload)
                }
            }
        }
        recyclerView.addOnScrollListener(mScrollListener!!)
    }

    fun setLoadingListItem(loadingListItem: LoadingListItem?) {
        this.loadingListItem = loadingListItem ?: MockLoadingListItem()
    }

    protected fun isLoadingItemAdded(): Boolean {
        return mItems.isNotEmpty() && mItems.last() is LoadingListItem
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