package com.haizo.poc.ui.viewholder.row

import androidx.databinding.ViewDataBinding
import com.haizo.generaladapter.ListItemCallback
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder
import com.haizo.poc.BR
import com.haizo.poc.model.ModelType2

class Type2ViewHolder(private val viewDataBinding: ViewDataBinding, callback: ListItemCallback?) :
    BaseBindingViewHolder<ModelType2>(viewDataBinding, callback) {

    init {
        attachClickListener(itemView)
    }

    override fun draw(listItem: ModelType2) {
        super.draw(listItem)
        viewDataBinding.setVariable(BR.model, listItem)
        viewDataBinding.executePendingBindings()
    }
}