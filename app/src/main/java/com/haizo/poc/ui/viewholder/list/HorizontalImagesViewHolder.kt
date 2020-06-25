package com.haizo.poc.ui.viewholder.list

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.haizo.generaladapter.ListItemCallback
import com.haizo.generaladapter.adapter.GeneralBindingListAdapter
import com.haizo.generaladapter.utils.ItemPaddingDecoration
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder
import com.haizo.poc.databinding.RowRvHorizontalImagesBinding
import com.haizo.poc.model.ImagesListModel

class HorizontalImagesViewHolder(viewDataBinding: ViewDataBinding, callback: ListItemCallback?) :
    BaseBindingViewHolder<ImagesListModel>(viewDataBinding, callback) {

    private val binding: RowRvHorizontalImagesBinding by lazy {
        viewDataBinding as RowRvHorizontalImagesBinding
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
        binding.recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.addItemDecoration(ItemPaddingDecoration(5, 5))
        binding.recyclerView.adapter = adapterBinding
        adapterBinding.setItemsToFitInScreen(context, 2.6f)
    }
}
