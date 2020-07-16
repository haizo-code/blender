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
import java.util.ArrayList

class LoadMoreHelper<T>(private val mAdapter: RecyclerView.Adapter<*>, val pageSize: Int = 10) {

    private var mItems: MutableList<T> = ArrayList()
    private val mLoadingThreshold = 3
    private var mCurrentPage = 1
    private lateinit var mRecyclerView: RecyclerView
    private var loadMoreListener: LoadMoreListener? = null
    var isLoadMoreEnabled = true

    fun setupLoadMore(recyclerView: RecyclerView, mItems: MutableList<T>, loadMoreListener: LoadMoreListener?,
        autoShowLoadingItem: Boolean = true) {
        this.mRecyclerView = recyclerView
        this.loadMoreListener = loadMoreListener
        this.mItems = mItems
        if (recyclerView.layoutManager is LinearLayoutManager) {
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    // To check if at the bottom of recycler view
                    if (mItems.size < pageSize) return
                    if (linearLayoutManager!!.findLastCompletelyVisibleItemPosition() >= mItems.size - mLoadingThreshold) {
                        if (isLoadMoreEnabled && loadMoreListener != null && !isLoadingItemAdded) {
                            if (isSpammingCalls) return
                            if (mItems.size >= mCurrentPage * pageSize) {
                                if (autoShowLoadingItem) addLoadMoreView()
                                loadMoreListener.onLoadMore(++mCurrentPage)
                            }
                        }
                    }
                }
            })
        }
    }

    private fun addLoadMoreView() {
        @Suppress("UNCHECKED_CAST")
        val loadingObj = LoadingObj() as T
        if (!isLoadingItemAdded) {
            mItems.add(loadingObj)
            mRecyclerView.post { mAdapter.notifyItemInserted(mItems.size - 1) }
        }
    }

    private val isLoadingItemAdded: Boolean
        get() {
            if (mItems.isNotEmpty()) {
                val lastIndex = mItems.size - 1
                val `object` = mItems[lastIndex]
                return `object` is LoadingObj
            }
            return false
        }

    fun removeLoadMoreIfExists() {
        if (mItems.isNotEmpty()) {
            val lastIndex = mItems.size - 1
            if (mItems[lastIndex] is LoadingObj) {
                mItems.removeAt(lastIndex)
                mAdapter.notifyItemRemoved(lastIndex)
            }
        }
        loadMoreListener?.onLoadMoreFinished()
    }

    fun addMoreItems(collection: Collection<T>?) {
        removeLoadMoreIfExists()
        if (collection != null) {
            val positionStart = mItems.size + 1
            mItems.addAll(collection)
            mAdapter.notifyItemRangeInserted(positionStart, mItems.size)
        }
    }

    fun resetPage() {
        mCurrentPage = 1
    }

    fun setCurrentPage(currentPage: Int) {
        this.mCurrentPage = currentPage
    }

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