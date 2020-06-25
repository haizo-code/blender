package com.haizo.generaladapter

import com.haizo.generaladapter.model.ListItemType
import com.haizo.generaladapter.viewholders.BlankViewHolder
import com.haizo.generaladapter.viewholders.LoadingViewHolder

internal object MainListItemTypes {
    //////////////
    // Keep this at first, so in case there is a listItem with no type then this will be the default
    var NONE = ListItemType(BlankViewHolder::class.java, 0, "NONE")
    //////////////

    var LOADING = ListItemType(LoadingViewHolder::class.java,
        R.layout.row_loading, "LOADING")
}