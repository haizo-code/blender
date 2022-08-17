package com.haizo.sample.ui.viewholder

import com.haizo.generaladapter.model.ViewHolderContract
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder
import com.haizo.sample.R
import com.haizo.sample.databinding.RowLoadingBinding
import com.haizo.sample.model.LoadingItem

//####################################################//
//#################### Contract ######################//
//####################################################//

val LOADING_VIEW_HOLDER_CONTRACT = ViewHolderContract(
    viewHolderClass = LoadingViewHolder::class.java,
    layoutResId = R.layout.row_loading
)

//####################################################//
//################## ViewHolder ######################//
//####################################################//

class LoadingViewHolder constructor(
    binding: RowLoadingBinding
) : BaseBindingViewHolder<LoadingItem>(binding) {
    override fun onBind(listItem: LoadingItem) {}
}