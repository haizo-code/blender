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

import com.haizo.generaladapter.ItemTypesPool

/**
 * Create by Farouq Afghani
 * @param viewHolderClass: The class of the ViewHolder that will be used with the view [layoutResId]
 * @param layoutResId: The layout resource for the view that will be associated with the passed ViewHolder [viewHolderClass]
 * @param itemName: This name will be helpful in debugging phase to know which ListItemType you are using for the selected ListItem
 */
class ListItemType(val viewHolderClass: Class<*>, val layoutResId: Int, private val itemName: String = "") {

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