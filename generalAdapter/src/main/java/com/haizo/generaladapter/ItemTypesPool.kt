package com.haizo.generaladapter

import android.util.SparseArray
import com.haizo.generaladapter.model.ListItemType

object ItemTypesPool {

    private val sTypeItemMap = SparseArray<ListItemType>()

    fun getItemType(type: Int): ListItemType {
        return sTypeItemMap.get(type) ?: MainListItemTypes.NONE
    }

    fun put(itemViewType: Int, listItemType: ListItemType) {
        sTypeItemMap.put(itemViewType, listItemType)
    }
}