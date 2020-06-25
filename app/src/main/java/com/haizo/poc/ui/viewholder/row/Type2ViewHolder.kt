package com.haizo.poc.ui.viewholder.row

import androidx.databinding.ViewDataBinding
import com.haizo.generaladapter.ListItemCallback
import com.haizo.generaladapter.viewholders.BaseViewHolder
import com.haizo.poc.BR
import com.haizo.poc.data.model.ModelType2

class Type2ViewHolder(private val binding: ViewDataBinding, callback: ListItemCallback?) : BaseViewHolder<ModelType2>(binding, callback) {

    init {
        attachClickListener(itemView)
    }

    override fun draw(listItem: ModelType2) {
        super.draw(listItem)
        binding.setVariable(BR.model, listItem)
        binding.executePendingBindings()
    }
}