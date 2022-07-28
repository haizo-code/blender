package com.haizo.sample.ui.viewholder.list

import androidx.recyclerview.widget.LinearLayoutManager
import com.haizo.generaladapter.listadapter.BlenderListAdapter
import com.haizo.generaladapter.utils.EdgeHorizontalItemPaddingDecoration
import com.haizo.generaladapter.utils.ItemPaddingDecoration
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder
import com.haizo.sample.callbacks.StoryActionCallback
import com.haizo.sample.databinding.RowStoriesRvBinding
import com.haizo.sample.model.StoriesList

class HorizontalStoriesViewHolder constructor(
    private val viewDataBinding: RowStoriesRvBinding,
    actionCallback: StoryActionCallback
) : BaseBindingViewHolder<StoriesList>(viewDataBinding, actionCallback) {

    private val adapterBinding: BlenderListAdapter by lazy {
        BlenderListAdapter(context = context, actionCallback)
    }

    init {
        setupRecyclerView()
    }

    override fun onBind(listItem: StoriesList) {
        adapterBinding.submitList(listItem.list.toList())
    }

    private fun setupRecyclerView() {
        viewDataBinding.recyclerView.let {
            it.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            it.addItemDecoration(ItemPaddingDecoration(5))
            it.addItemDecoration(EdgeHorizontalItemPaddingDecoration(paddingStart = 10))
            it.adapter = adapterBinding
        }
        adapterBinding.setItemsToFitInScreen(3.6f)
    }
}