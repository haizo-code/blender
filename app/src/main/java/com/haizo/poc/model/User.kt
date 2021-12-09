package com.haizo.poc.model

import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ListItemType
import com.haizo.poc.util.ListItemTypes

data class User(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val location: String,
    val imageUrl: String
) : ListItem {
    override var listItemType: ListItemType = ListItemTypes.ITEM_USER_CARD

    override fun itemUniqueIdentifier(): String {
        return id
    }

    override fun areContentsTheSame(newItem: ListItem): Boolean {
        return if (newItem is User) {
            this == newItem
        } else false
    }
}