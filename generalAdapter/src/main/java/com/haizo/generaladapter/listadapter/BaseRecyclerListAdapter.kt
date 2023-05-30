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

import androidx.recyclerview.widget.LinearLayoutManager
import com.haizo.generaladapter.loadmore.listadapter.LoadMoreListAdapter
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ListItemsContainer
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

    fun getListItem(itemUniqueIdentifier: String): ListItem? {
        return getListItem(indexOf(itemUniqueIdentifier))
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

    /**
     * Add items to the list in specific index and submit it
     * Note: this method have a fallback in case index is out-of-boundary, which it will add the item as the last item
     */
    fun addItemToList(index: Int, listItem: ListItem, commitCallback: Runnable? = null) {
        val list = currentList.toMutableList().also { it.add(index.coerceAtMost(it.size), listItem) }
        submitList(list, commitCallback)
    }

    /**
     * Add items to the list and submit it
     */
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

    /**
     * @param [container]: the container listItem that holds the sub ListItem
     * @param [innerListItem]: the updated inner listItem
     * Used when the item has been updated by reference, so in this case the DiffUtil will not see the change
     *
     * ---> Make sure to set your Recyclerview with (supportsChangeAnimations = false) to avoid flicking the whole inner list
     */
    open fun <T : ListItem> updateInnerListItem(container: ListItemsContainer<T>, innerListItem: T) {
        container.updateItemInList(updatedListItem = innerListItem)
        updateItemData(item = container, forceNotify = true)
    }

    /**
     * @param [containerUniqueId]: the container listItem Unique ID that holds the sub ListItem
     * @param [innerListItem]: the updated inner listItem
     * Used when the item has been updated by reference, so in this case the DiffUtil will not see the change
     *
     * ---> Make sure to set your Recyclerview with (supportsChangeAnimations = false) to avoid flicking the whole inner list
     */
    @Suppress("UNCHECKED_CAST")
    open fun <T : ListItem> updateInnerListItem(containerUniqueId: String, innerListItem: T) {
        val container = getListItem(containerUniqueId) as? ListItemsContainer<T>
        container?.let { updateInnerListItem(it, innerListItem) }
    }

    @Suppress("UNCHECKED_CAST")
    open fun <T : ListItem> getInnerListItem(containerUniqueId: String, innerItemUniqueId: String): T? {
        val container = getListItem(containerUniqueId) as? ListItemsContainer<T>
        return container?.let {
            it.getInnerList().first { innerList -> innerList.itemUniqueIdentifier() == innerItemUniqueId }
        }
    }

    @Suppress("UNCHECKED_CAST")
    open fun getInnerListItemIndex(containerUniqueId: String, innerItemUniqueId: String): Int {
        val container = getListItem(containerUniqueId) as? ListItemsContainer<ListItem>
        return container?.let {
            it.getInnerList().indexOfFirst { innerList -> innerList.itemUniqueIdentifier() == innerItemUniqueId }
        } ?: -1
    }

    /**
     * This method will [notifyItemRangeChanged]" for (previous x items) to (next x items) using the current item as a pivot
     * This method can be defined as the light-version of [notifyDataSetChanged]
     */
    fun notifyItemRangeChangedForSurroundingItems(range: Int = 5) {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val currentPosition = layoutManager.findFirstVisibleItemPosition()

        val start = (currentPosition - range).coerceAtLeast(0)
        val end = (currentPosition + range).coerceAtMost(itemCount - 1)

        if (start <= end) {
            notifyItemRangeChanged(start, end - start + 1)
        }
    }

    private fun isValidItemPos(pos: Int): Boolean {
        return pos in 0 until itemCount
    }
}
