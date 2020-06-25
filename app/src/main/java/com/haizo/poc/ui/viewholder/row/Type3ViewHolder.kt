package com.haizo.poc.ui.viewholder.row

import androidx.databinding.ViewDataBinding
import com.haizo.generaladapter.ListItemCallback
import com.haizo.generaladapter.viewholders.BaseViewHolder
import com.haizo.poc.BR
import com.haizo.poc.data.model.ModelType3

class Type3ViewHolder(private val binding: ViewDataBinding, callback: ListItemCallback?) : BaseViewHolder<ModelType3>(binding, callback) {

    init {
        attachClickListener(itemView)
    }

    override fun draw(listItem: ModelType3) {
        super.draw(listItem)
        binding.setVariable(BR.model, listItem)
        binding.executePendingBindings()
    }
}