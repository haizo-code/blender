package com.haizo.generaladapter

import com.haizo.generaladapter.model.ListItemType
import com.haizo.generaladapter.viewholders.BlankViewHolder
import com.haizo.generaladapter.viewholders.LoadingViewHolder

internal object MainListItemTypes {
    var NONE = ListItemType(BlankViewHolder::class.java, R.layout.row_blank, "NONE")
    var LOADING = ListItemType(LoadingViewHolder::class.java, R.layout.row_loading, "LOADING")
}