package com.haizo.generaladapter.listitems

import com.haizo.generaladapter.MainViewHolderContracts
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ViewHolderContract

class MockListItem constructor(val id: String) : ListItem {
    override fun itemUniqueIdentifier() = id
    override fun areContentsTheSame(newItem: ListItem) = true
    override val viewHolderContract: ViewHolderContract = MainViewHolderContracts.NONE
}