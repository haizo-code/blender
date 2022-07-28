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

/**
 * This class will handle the loadMore process
 * @param [adapter] recyclerview adapter
 * @param [pageSize]: page size
 * @param [loadingThreshold]: on index(n) from the end of the list, trigger the loadMore
 */
class LoadMoreHelper<T> constructor(
    private val adapter: RecyclerView.Adapter<*>,
    val pageSize: Int = 10,
    val loadingThreshold: Int = 3
) {

    private var mItems: MutableList<T> = ArrayList()
    private var mCurrentPage = 1
    private lateinit var mRecyclerView: RecyclerView
    private var loadMoreListener: LoadMoreListener? = null
    var isLoadMoreEnabled = true

    /**
     * Setup the loadMore behavior
     * @param [recyclerView]
     * @param [items]
     * @param [loadMoreListener]
     */
    fun setupLoadMore(recyclerView: RecyclerView, items: MutableList<T>, loadMoreListener: LoadMoreListener?,
        autoShowLoadingItem: Boolean = true) {
        this.mRecyclerView = recyclerView
        this.loadMoreListener = loadMoreListener
        this.mItems = items
        if (recyclerView.layoutManager is LinearLayoutManager) {
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    // To check if at the bottom of recycler view
                    if (items.size < pageSize) return
                    if (linearLayoutManager!!.findLastCompletelyVisibleItemPosition() >= items.size - loadingThreshold) {
                        if (isLoadMoreEnabled && loadMoreListener != null && !isLoadingItemAdded) {
                            if (isSpammingCalls) return
                            if (items.size >= mCurrentPage * pageSize) {
                                if (autoShowLoadingItem) addLoadMoreView()
                                loadMoreListener.onLoadMore(++mCurrentPage)
                            }
                        }
                    }
                }
            })
        }
    }

    /**
     * Add loadMore view to the recyclerView
     */
    private fun addLoadMoreView() {
        @Suppress("UNCHECKED_CAST")
        val loadingObj = LoadingObj() as T
        if (!isLoadingItemAdded) {
            mItems.add(loadingObj)
            adapter.notifyItemInserted(mItems.size - 1)
        }
    }

    /**
     * @return True if the loading item is visible
     */
    private val isLoadingItemAdded: Boolean
        get() {
            if (mItems.isNotEmpty()) {
                val lastIndex = mItems.size - 1
                val `object` = mItems[lastIndex]
                return `object` is LoadingObj
            }
            return false
        }

    /**
     * Remove the loadMore view if exists / Trigger onLoadMoreFinished()
     */
    fun removeLoadMoreIfExists() {
        if (mItems.isNotEmpty()) {
            val lastIndex = mItems.size - 1
            if (mItems[lastIndex] is LoadingObj) {
                mItems.removeAt(lastIndex)
                adapter.notifyItemRemoved(lastIndex)
            }
        }
        loadMoreListener?.onLoadMoreFinished()
    }

    /**
     * Add more items to the main listItem for the adapter
     */
    fun addMoreItems(collection: Collection<T>?) {
        removeLoadMoreIfExists()
        if (collection != null) {
            val positionStart = mItems.size + 1
            mItems.addAll(collection)
            adapter.notifyItemRangeInserted(positionStart, mItems.size)
        }
    }

    /**
     * Reset the current page number to 1
     */
    fun resetPage() {
        mCurrentPage = 1
    }

    /**
     * Set the current page for the loadMore
     * @param [currentPage]
     */
    fun setCurrentPage(currentPage: Int) {
        this.mCurrentPage = currentPage
    }

    /**
     * Handles the spam of loadMore when repeat fast swipe to scroll down.
     */
    private var lastClickTime: Long = 0
    private val isSpammingCalls: Boolean
        get() {
            val clickTime = System.currentTimeMillis()
            if (System.currentTimeMillis() - lastClickTime < 500) {
                lastClickTime = clickTime
                return true
            }
            lastClickTime = clickTime
            return false
        }

}