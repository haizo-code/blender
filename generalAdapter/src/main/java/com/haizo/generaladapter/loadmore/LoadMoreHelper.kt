package com.haizo.generaladapter.loadmore

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

/**
 * Created by Farouq Afghani on 2019-02-21.
 */
class LoadMoreHelper(private val adapter: RecyclerView.Adapter<*>, val pageSize: Int = 10) {

    private var mItems: MutableList<Any?>? = ArrayList()
    private val loadingThreshold = 3
    private var currentPage = 1
    private var recyclerView: RecyclerView? = null
    var isLoadMoreEnabled = true

    fun setupLoadMore(recyclerView: RecyclerView, mItems: MutableList<*>?, loadMoreListener: LoadMoreListener?) {
        this.recyclerView = recyclerView
        if (this.recyclerView == null) return
        this.mItems = mItems as MutableList<Any?>?
        if (recyclerView.layoutManager is LinearLayoutManager) {
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    // To check if at the bottom of recycler view
                    if (mItems != null && mItems.size < pageSize) return
                    if (linearLayoutManager!!.findLastCompletelyVisibleItemPosition() >= mItems!!.size - loadingThreshold) {
                        if (isLoadMoreEnabled && loadMoreListener != null && !isLoadingItemAdded) {
                            if (isSpammingCalls) return
                            if (mItems.size >= currentPage * pageSize) {
                                addLoadMoreView()
                                loadMoreListener.onLoadMore(++currentPage)
                            }
                        }
                    }
                }
            })
        }
    }

    private fun addLoadMoreView() {
        val loadingObj = LoadingObj()
        if (mItems != null) {
            if (!isLoadingItemAdded) {
                mItems?.add(loadingObj)
                recyclerView?.post { adapter.notifyItemInserted(mItems!!.size - 1) }
            }
        }
    }

    private val isLoadingItemAdded: Boolean
        get() {
            if (mItems != null && mItems!!.isNotEmpty()) {
                val lastIndex = mItems!!.size - 1
                val `object` = mItems!![lastIndex]
                return `object` is LoadingObj
            }
            return false
        }

    fun removeLoadMoreIfExists() {
        if (mItems != null && mItems!!.isNotEmpty()) {
            val lastIndex = mItems!!.size - 1
            if (mItems!![lastIndex] is LoadingObj) {
                mItems!!.removeAt(lastIndex)
                adapter.notifyItemRemoved(lastIndex)
            }
        }
    }

    fun addMoreItems(collection: List<Any?>?) {
        removeLoadMoreIfExists()
        if (collection != null) {
            val positionStart = mItems!!.size + 1
            mItems!!.addAll(collection)
            adapter.notifyItemRangeInserted(positionStart, mItems!!.size)
        }
    }

    fun resetPage() {
        currentPage = 1
    }

    fun setCurrentPage(currentPage: Int) {
        this.currentPage = currentPage
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