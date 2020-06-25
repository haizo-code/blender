package com.haizo.generaladapter

import android.view.View
import com.haizo.generaladapter.model.ListItem

interface ListItemCallback {
    fun onItemClicked(view: View, listItem: ListItem, position: Int = -1, actionId: Int = 0)
}