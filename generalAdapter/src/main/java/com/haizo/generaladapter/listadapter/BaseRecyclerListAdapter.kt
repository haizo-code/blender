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
package com.haizo.generaladapter.listadapter

import com.haizo.generaladapter.loadmore.listadapter.LoadMoreListAdapter
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder

abstract class BaseRecyclerListAdapter internal constructor() : LoadMoreListAdapter() {

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun getItemViewType(index: Int): Int {
        val item = currentList[index]
        return item.viewHolderContract.mItemViewType
    }

    override fun onFailedToRecycleView(holder: BaseBindingViewHolder<ListItem>): Boolean {
        // NOTE: this is a workaround to not create more ViewHolder instances while scrolling RecyclerView and
        //  there is something in the ViewHolder denying the RecyclerView from caching it
        return true
    }

    //####################################################//
    //################## Exposed Methods #################//
    //####################################################//

    @Suppress("RedundantOverride")
    override fun getCurrentList(): List<ListItem> {
        return super.getCurrentList()
    }

    //####################################################//

    fun getListItem(pos: Int): ListItem? {
        return if (isValidItemPos(pos)) super.getItem(pos) else null
    }

    open fun indexOf(item: ListItem): Int {
        return currentList.indexOfFirst { it.itemUniqueIdentifier() == item.itemUniqueIdentifier() }
    }

    open fun indexOf(itemUniqueIdentifier: String): Int {
        return currentList.indexOfFirst { it.itemUniqueIdentifier() == itemUniqueIdentifier }
    }

    //####################################################//
    //################# Helper Methods ###################//
    //####################################################//

    fun removeItemFromList(listItem: ListItem, commitCallback: Runnable? = null) {
        val list = currentList.toMutableList().also { listItems ->
            val item = indexOf(listItem)
            listItems.removeAt(item)
        }
        submitList(list, commitCallback)
    }

    fun removeItemFromList(itemUniqueIdentifier: String, commitCallback: Runnable? = null) {
        val list = currentList.toMutableList().also { listItems ->
            val item = indexOf(itemUniqueIdentifier)
            listItems.removeAt(item)
        }
        submitList(list, commitCallback)
    }

    fun addItemToList(index: Int, listItem: ListItem, commitCallback: Runnable? = null) {
        val list = currentList.toMutableList().also { it.add(index, listItem) }
        submitList(list, commitCallback)
    }

    fun addItemToList(listItem: ListItem, commitCallback: Runnable? = null) {
        val list = currentList.toMutableList().also { it.add(listItem) }
        submitList(list, commitCallback)
    }

    /**
     * @param [forceNotify]: True if you want to force notify this item's ViewHolder
     * Used when the item has been updated by reference, so in this case the DiffUtil will not see the change,
     **/
    open fun updateItemData(item: ListItem, forceNotify: Boolean = false) {
        val pos = indexOf(item)
        if (pos != -1) {
            currentList.toMutableList().let { newList ->
                newList[pos] = item
                submitList(newList) { if (forceNotify) notifyItemChanged(pos) }
            }
        }
    }

    private fun isValidItemPos(pos: Int): Boolean {
        return pos in 0 until itemCount
    }
}
