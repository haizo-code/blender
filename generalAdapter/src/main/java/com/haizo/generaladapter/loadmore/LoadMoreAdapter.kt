package com.haizo.generaladapter.loadmore

import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Farouq Afghani on 2019-02-21.
 */
abstract class LoadMoreAdapter<VH : RecyclerView.ViewHolder?> : RecyclerView.Adapter<VH>() {

    private var loadMoreHelper: LoadMoreHelper? = null

    fun setupLoadMore(recyclerView: RecyclerView?, loadMoreListener: LoadMoreListener?, pageSize: Int = 10) {
        if (recyclerView == null || loadMoreListener == null) return
        loadMoreHelper = LoadMoreHelper(this,pageSize)
        loadMoreHelper?.setupLoadMore(recyclerView, items, loadMoreListener)
    }

    fun addMoreItems(list: List<*>?) {
        loadMoreHelper?.addMoreItems(list)
    }

    fun removeLoadMoreIfExists() {
        loadMoreHelper?.removeLoadMoreIfExists()
    }

    fun resetPageNumber() {
        loadMoreHelper?.resetPage()
    }

    fun setCurrentPageNumber(pageNumber: Int) {
        loadMoreHelper?.setCurrentPage(pageNumber)
    }

    var isLoadMoreEnabled: Boolean
        get() = loadMoreHelper?.isLoadMoreEnabled ?: false
        set(isEnabled) {
            loadMoreHelper?.isLoadMoreEnabled = isEnabled
        }

    protected abstract val items: MutableList<*>?
}