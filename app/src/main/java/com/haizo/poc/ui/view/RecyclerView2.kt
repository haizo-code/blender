package com.haizo.poc.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.haizo.generaladapter.adapter.GeneralListAdapter
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.ListItemCallback
import com.haizo.generaladapter.loadmore.LoadMoreListener
import com.haizo.poc.R
import kotlinx.android.synthetic.main.fragment_list.view.recyclerView
import kotlinx.android.synthetic.main.fragment_list.view.swipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_list.view.tvEmpty

class RecyclerView2 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    internal val adapter: GeneralListAdapter by lazy {
        GeneralListAdapter(context = context,
            listItemCallback = listItemCallback)
    }

    init {
        View.inflate(context, R.layout.fragment_list, this)
    }

    private var layoutManager: LinearLayoutManager? = null
    private var listItemCallback: ListItemCallback? = null
    private var loadMoreListener: LoadMoreListener? = null
    private var onRefreshListener: (() -> Unit)? = null
    private var onEmptyListListener: ((Boolean) -> Unit)? = null

    fun setup(
        layoutManager: LinearLayoutManager? = LinearLayoutManager(context),
        listItemCallback: ListItemCallback? = null,
        onRefreshListener: (() -> Unit)? = null,
        onEmptyListListener: ((Boolean) -> Unit)? = null,
        loadMoreListener: LoadMoreListener? = null
    ) {
        this.layoutManager = layoutManager
        this.listItemCallback = listItemCallback
        this.onRefreshListener = onRefreshListener
        this.onEmptyListListener = onEmptyListListener
        this.loadMoreListener = loadMoreListener

        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter
        setupSwipeToRefresh()
        adapter.listItemCallback = listItemCallback
        adapter.setupLoadMore(recyclerView, loadMoreListener)
    }

    fun get(): RecyclerView? {
        return recyclerView
    }

    fun showLoading() {
        if (swipeRefreshLayout?.isRefreshing == false) {
            swipeRefreshLayout.isRefreshing = true
        }
    }

    fun updateList(list: List<ListItem>?) {
        adapter.updateList(list)
        onListUpdated(!isEmpty())
        removeLoadingViews()
    }

    fun addItems(list: List<ListItem>) {
        removeLoadingViews()

        if (loadMoreListener != null) {
            adapter.addMoreItems(list)
            return
        } else {
            val positionStart = list.size
            adapter.addAll(list)
            if (recyclerView?.isNestedScrollingEnabled == true) {
                adapter.notifyDataSetChanged()
            }else{
                adapter.notifyItemRangeInserted(positionStart, list.size)
            }
        }
        onListUpdated(!isEmpty())
    }

    fun updateLayoutManager(layoutManager: LinearLayoutManager?) {
        layoutManager ?: return
        recyclerView?.layoutManager = layoutManager
        adapter.setupLoadMore(recyclerView, loadMoreListener)
    }

    fun isFirstItemVisible(): Boolean {
        return layoutManager?.findFirstVisibleItemPosition() == 0
    }

    fun isEmpty(): Boolean {
        return adapter.itemCount == 0
    }

    fun getCount(): Int {
        return adapter.itemCount
    }

    fun reset(isFirstPage: Boolean) {
        if (isFirstPage) {
            adapter.resetPageNumber()
            adapter.clear()
        }
    }

    private fun setupSwipeToRefresh() {
        val isSwipeRefreshEnabled = onRefreshListener != null
        if (!isSwipeRefreshEnabled) swipeRefreshLayout?.isRefreshing = false
        swipeRefreshLayout?.isEnabled = isSwipeRefreshEnabled
        swipeRefreshLayout?.setOnRefreshListener {
            // Reset the page number for the adapter
            adapter.resetPageNumber()
            // Trigger the callback for onRefresh
            onRefreshListener?.invoke()
        }
    }

    fun removeLoadingViews() {
        swipeRefreshLayout?.isRefreshing = false
        adapter.removeLoadMoreIfExists()
    }

    private fun onListUpdated(hasContent: Boolean = true) {
        if (onEmptyListListener != null) {
            onEmptyListListener?.invoke(hasContent)
        } else {
            tvEmpty?.visibility = if (hasContent) View.GONE else View.VISIBLE
        }
    }
}
