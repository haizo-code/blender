package com.haizo.generaladapter.model

import com.haizo.generaladapter.ItemTypesPool

class ListItemType(
    val viewHolderClass: Class<*>,
    val layoutResId: Int,
    val itemName: String = ""
) {

    var itemViewType: Int = 0

    init {
        this.itemViewType = type++
        ItemTypesPool.put(itemViewType, this)
    }

    override fun hashCode(): Int {
        return itemViewType
    }

    override fun equals(other: Any?): Boolean {
        return other === this || other is ListItemType && itemViewType == other.itemViewType
    }

    companion object {
        var type: Int = 0
    }
}