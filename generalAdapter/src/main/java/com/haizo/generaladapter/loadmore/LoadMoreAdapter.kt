package com.haizo.generaladapter.loadmore

import android.util.Log
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

    fun addMoreItems(list: Collection<Any>?) {
        if (loadMoreHelper == null) Log.d("LoadMoreHelper", ERROR_NOT_INITIALIZED)
        loadMoreHelper?.addMoreItems(list)
    }

    fun removeLoadMoreIfExists() {
        if (loadMoreHelper == null) Log.d("LoadMoreHelper", ERROR_NOT_INITIALIZED)
        loadMoreHelper?.removeLoadMoreIfExists()
    }

    fun resetPageNumber() {
        if (loadMoreHelper == null) Log.d("LoadMoreHelper", ERROR_NOT_INITIALIZED)
        loadMoreHelper?.resetPage()
    }

    fun setCurrentPageNumber(pageNumber: Int) {
        if (loadMoreHelper == null) Log.d("LoadMoreHelper", ERROR_NOT_INITIALIZED)
        loadMoreHelper?.setCurrentPage(pageNumber)
    }

    var isLoadMoreEnabled: Boolean
        get() = loadMoreHelper?.isLoadMoreEnabled ?: false
        set(isEnabled) {
            loadMoreHelper?.isLoadMoreEnabled = isEnabled
        }

    protected abstract val items: MutableList<*>

    companion object{
        private const val ERROR_NOT_INITIALIZED = "You forgot to setup the LoadMore helper, Please set it up and try again"
    }
}