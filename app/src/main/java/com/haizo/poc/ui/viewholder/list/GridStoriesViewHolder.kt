package com.haizo.poc.ui.viewholder.list

import androidx.recyclerview.widget.GridLayoutManager
import com.haizo.generaladapter.listadapter.BlenderListAdapter
import com.haizo.generaladapter.utils.ItemPaddingDecoration
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder
import com.haizo.poc.callbacks.StoryActionCallback
import com.haizo.poc.databinding.RowStoriesRvBinding
import com.haizo.poc.model.StoriesListItem

class GridStoriesViewHolder(private val viewDataBinding: RowStoriesRvBinding, actionCallback: StoryActionCallback) :
    BaseBindingViewHolder<StoriesListItem>(viewDataBinding, actionCallback) {

    private val adapterBinding: BlenderListAdapter by lazy {
        BlenderListAdapter(context = context, actionCallback)
    }

    init {
        setupRecyclerView()
    }

    override fun onBind(listItem: StoriesListItem) {
        adapterBinding.submitList(listItem.list.toList())
    }

    private fun setupRecyclerView() {
        viewDataBinding.recyclerView.let {
            it.layoutManager = GridLayoutManager(context, 2)
            it.addItemDecoration(ItemPaddingDecoration(5))
            it.adapter = adapterBinding
        }
    }
}
