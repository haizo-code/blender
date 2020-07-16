package com.haizo.poc.ui.viewholder.row

import androidx.databinding.ViewDataBinding
import com.haizo.generaladapter.ListItemCallback
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder
import com.haizo.poc.BR
import com.haizo.poc.model.UserCardModel

class UserCardViewHolder(private val viewDataBinding: ViewDataBinding, callback: ListItemCallback?) :
    BaseBindingViewHolder<UserCardModel>(viewDataBinding, callback) {

    init {
        attachClickListener(itemView)
    }

    override fun onBind(listItem: UserCardModel) {
        viewDataBinding.setVariable(BR.model, listItem)
        viewDataBinding.executePendingBindings()
    }
}