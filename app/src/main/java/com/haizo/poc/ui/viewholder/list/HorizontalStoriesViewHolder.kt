package com.haizo.poc.ui.viewholder.list

import androidx.recyclerview.widget.LinearLayoutManager
import com.haizo.generaladapter.adapter.GeneralBindingListAdapter
import com.haizo.generaladapter.interfaces.BaseActionCallback
import com.haizo.generaladapter.utils.ItemPaddingDecoration
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder
import com.haizo.poc.databinding.RowRvImagesBinding
import com.haizo.poc.model.ImagesListModel

class HorizontalStoriesViewHolder(private val viewDataBinding: RowRvImagesBinding,
    actionCallback: BaseActionCallback?) :
    BaseBindingViewHolder<ImagesListModel>(viewDataBinding, actionCallback) {

    private val adapterBinding: GeneralBindingListAdapter by lazy {
        GeneralBindingListAdapter(context = context)
    }

    init {
        setupRecyclerView()
    }

    override fun onBind(listItem: ImagesListModel) {
        adapterBinding.updateList(listItem.list)
    }

    private fun setupRecyclerView() {
        viewDataBinding.recyclerView.let {
            it.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            it.addItemDecoration(ItemPaddingDecoration(5))
            it.adapter = adapterBinding
        }
        adapterBinding.setItemsToFitInScreen(context, 3.6f)
    }
}
