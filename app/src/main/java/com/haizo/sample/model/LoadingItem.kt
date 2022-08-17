package com.haizo.sample.model

import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.LoadingListItem
import com.haizo.generaladapter.model.ViewHolderContract
import com.haizo.sample.ui.viewholder.LOADING_VIEW_HOLDER_CONTRACT

class LoadingItem : LoadingListItem {
    override var viewHolderContract: ViewHolderContract = LOADING_VIEW_HOLDER_CONTRACT
    override fun areContentsTheSame(newItem: ListItem): Boolean = true
}
