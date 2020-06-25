package com.haizo.generaladapter

import com.haizo.generaladapter.model.ListItemType
import com.haizo.generaladapter.viewholders.BlankViewHolder
import com.haizo.generaladapter.viewholders.LoadingViewHolder

object MainListItemTypes {
    var NONE = ListItemType(BlankViewHolder::class.java, 0, "NONE")
    var LOADING = ListItemType(LoadingViewHolder::class.java,
        R.layout.row_loading, "LOADING")
}