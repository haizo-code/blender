package com.haizo.poc.ui.viewholder.row

import androidx.databinding.ViewDataBinding
import com.haizo.generaladapter.ListItemCallback
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder
import com.haizo.poc.BR
import com.haizo.poc.model.ModelType1

class Type1ViewHolder(private val viewDataBinding: ViewDataBinding, callback: ListItemCallback?) :
    BaseBindingViewHolder<ModelType1>(viewDataBinding, callback) {

    init {
        attachClickListener(itemView)
    }

    override fun draw(listItem: ModelType1) {
        super.draw(listItem)
        viewDataBinding.setVariable(BR.model, listItem)
        viewDataBinding.executePendingBindings()
    }
}