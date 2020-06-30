package com.haizo.poc.ui.viewholder.list

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.haizo.generaladapter.ListItemCallback
import com.haizo.generaladapter.adapter.GeneralBindingListAdapter
import com.haizo.generaladapter.utils.ItemPaddingDecoration
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder
import com.haizo.poc.databinding.RowRvImagesBinding
import com.haizo.poc.model.ImagesListModel

class HorizontalStoriesViewHolder(viewDataBinding: ViewDataBinding, callback: ListItemCallback?) :
    BaseBindingViewHolder<ImagesListModel>(viewDataBinding, callback) {

    private val binding: RowRvImagesBinding by lazy {
        viewDataBinding as RowRvImagesBinding
    }

    private val adapterBinding: GeneralBindingListAdapter by lazy {
        GeneralBindingListAdapter(context = context)
    }

    init {
        setupRecyclerView()
    }

    override fun draw(listItem: ImagesListModel) {
        super.draw(listItem)
        adapterBinding.updateList(listItem.list)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.let {
            it.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            it.addItemDecoration(ItemPaddingDecoration(5))
            it.adapter = adapterBinding
        }
        adapterBinding.setItemsToFitInScreen(context, 3.6f)
    }
}
