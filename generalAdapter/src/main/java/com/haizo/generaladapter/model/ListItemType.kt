/*
 * Copyright 2020 Farouq Afghani
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.haizo.generaladapter.model

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.haizo.generaladapter.ItemTypesPool
import com.haizo.generaladapter.interfaces.BaseActionCallback
import com.haizo.generaladapter.interfaces.ViewHolderExtras

/**
 * Create by Farouq Afghani
 * @param viewHolderClass: The class of the ViewHolder that will be used with the view [layoutResId]
 * @param layoutResId: The layout resource for the view that will be associated with the passed ViewHolder [viewHolderClass]
 * @param itemName: This name will be helpful in debugging phase to know which ListItemType you are using for the selected ListItem
 * @param callbackClass: The class of the BaseActionCallback that will be used in the [viewHolderClass]
 * @param extrasClass: The class of the ViewHolderExtras that will be used in the view [viewHolderClass]
 */
class ListItemType @JvmOverloads constructor(
    val viewHolderClass: Class<out RecyclerView.ViewHolder>,
    @LayoutRes val layoutResId: Int,
    val itemName: String = viewHolderClass.simpleName,
    var callbackClass: Class<out BaseActionCallback>? = null,
    var extrasClass: Class<out ViewHolderExtras>? = null
) {

    var mItemViewType: Int = 0

    init {
        this.mItemViewType = type++
        ItemTypesPool.put(mItemViewType, this)
    }

    override fun hashCode(): Int {
        return mItemViewType
    }

    override fun equals(other: Any?): Boolean {
        return other === this || other is ListItemType && mItemViewType == other.mItemViewType
    }

    companion object {
        var type: Int = 1
    }
}