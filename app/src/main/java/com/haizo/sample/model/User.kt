package com.haizo.sample.model

import com.haizo.generaladapter.model.ListItem
import com.haizo.sample.ui.viewholder.USER_VIEW_HOLDER_CONTRACT

data class User constructor(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val location: String,
    val imageUrl: String
) : ListItem {
    override var viewHolderContract = USER_VIEW_HOLDER_CONTRACT

    override fun itemUniqueIdentifier(): String {
        return id
    }

    override fun areContentsTheSame(newItem: ListItem): Boolean {
        return this == newItem
    }
}