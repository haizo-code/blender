package com.haizo.poc.ui.viewholder.list

import android.os.Handler
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.haizo.generaladapter.ListItemCallback
import com.haizo.generaladapter.loadmore.LoadMoreListener
import com.haizo.generaladapter.viewholders.BaseViewHolder
import com.haizo.poc.data.model.StoryListModel
import com.haizo.poc.databinding.RowRvHorizontalStoriesBinding
import com.haizo.generaladapter.utils.ItemPaddingDecoration

class HorizontalStoriesVH(binding: ViewDataBinding, callback: ListItemCallback?) : BaseViewHolder<StoryListModel>(binding, callback) {

    private val view : RowRvHorizontalStoriesBinding by lazy {
        binding as RowRvHorizontalStoriesBinding
    }

    init {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        view.recyclerView2.setup(
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false),
            loadMoreListener = object : LoadMoreListener {
                override fun onLoadMore(pageToLoad: Int) {
                    Handler().postDelayed({ view.recyclerView2.removeLoadingViews() }, 2000)
                    Toast.makeText(context, "Page: $pageToLoad", Toast.LENGTH_SHORT).show()
                }
            }
        )
        view.recyclerView2.adapter.setItemsToFitInScreen(context, 2.6f)
        view.recyclerView2.get()?.addItemDecoration(
            ItemPaddingDecoration(5, 5))
    }

    override fun draw(listItem: StoryListModel) {
        super.draw(listItem)
        view.recyclerView2.updateList(listItem.list)
    }
}
