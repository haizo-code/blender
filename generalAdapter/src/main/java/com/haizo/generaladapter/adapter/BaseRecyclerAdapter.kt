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
package com.haizo.generaladapter.adapter

import android.annotation.SuppressLint
import android.content.Context
import com.haizo.generaladapter.interfaces.AdapterActions
import com.haizo.generaladapter.loadmore.legacyadapter.LoadMoreAdapter
import com.haizo.generaladapter.model.ListItem

@SuppressLint("NotifyDataSetChanged")
abstract class BaseRecyclerAdapter(
    private val context: Context
) : AdapterActions, LoadMoreAdapter() {

    protected var mItems: MutableList<ListItem> = ArrayList()
    protected var mItemWidth: Float? = null

    /**
     * Clear all the list and reset page number of the LoadMore
     */
    override fun clear() {
        mItems.clear()
        notifyDataSetChanged()
        resetPageNumber()
    }

    /**
     * Add item to the adapter
     * @param listItem
     */
    override fun add(listItem: ListItem) {
        mItems.add(listItem)
        notifyItemInserted(mItems.size - 1)
    }

    /**
     * Add item to the adapter in a specific index
     * @param index
     * @param listItem
     */
    override fun add(index: Int, listItem: ListItem) {
        if (index in 0 until mItems.size) {
            mItems.add(index, listItem)
            notifyItemInserted(index)
        } else add(listItem)
    }

    /**
     * Add list of items to the adapter
     * @param listItems
     */
    override fun addAll(listItems: List<ListItem>) {
        mItems.addAll(listItems)
        notifyItemRangeInserted(mItems.size, mItems.size)
    }

    /**
     * Add list of items to the adapter in a specific index
     * @param index
     * @param listItems
     */
    override fun addAll(index: Int, listItems: List<ListItem>) {
        if (index in 0 until mItems.size) {
            mItems.addAll(index, listItems)
            notifyItemRangeInserted(index, listItems.size)
        } else addAll(listItems)
    }

    /**
     * Update a specific index
     * @param index
     * @param listItem
     */
    override operator fun set(index: Int, listItem: ListItem) {
        if (index in 0 until mItems.size) {
            mItems[index] = listItem
            notifyItemChanged(index)
        }
    }

    /**
     * Update the whole list with a new list
     * @param listItems
     */
    override fun updateList(listItems: List<ListItem>?) {
        clear()
        mItems.addAll(listItems ?: ArrayList())
        notifyDataSetChanged()
    }

    /**
     * Remove listItem at index
     * @param [index]
     */
    override fun remove(index: Int) {
        if (index in 0 until mItems.size) {
            mItems.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    /**
     * Remove the passed listItem from the main list
     * @param [listItem]
     */
    override fun remove(listItem: ListItem) {
        val indexOfItem = indexOf(listItem)
        if (indexOfItem != -1) {
            mItems.remove(listItem)
            notifyItemRemoved(indexOfItem)
        }
    }

    /**
     * Remove the passed listItems list from the main list
     * @param [items]: ListItems
     */
    override fun removeAll(items: List<ListItem>) {
        if (items.isNotEmpty()) {
            mItems.removeAll(items.toSet())
            notifyDataSetChanged()
        }
    }

    /**
     * @return index of the passed item (-1 if not found)
     * @param [listItem]
     */
    override fun indexOf(listItem: ListItem): Int {
        return mItems.indexOf(listItem)
    }

    /**
     * @return true if the main list contains a specific listItem
     * @param [listItem]
     */
    override fun contains(listItem: ListItem): Boolean {
        return mItems.contains(listItem)
    }

    /**
     * perform move item from index to index
     * @param from
     * @param to
     */
    override fun moveItem(from: Int, to: Int): Boolean {
        return try {
            mItems.removeAt(from)
            if (to < from) {
                mItems.add(to, mItems[from])
            } else {
                mItems.add(to - 1, mItems[from])
            }
            notifyItemMoved(from, to)
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * return the Item at index
     * @param [index]
     */
    override fun getItem(index: Int): ListItem? {
        return mItems[index]
    }

    /**
     * Set the number of the items that will fit in the screen (Horizontally), so for ex, 1.5 will show 1 and (half item/quarter of 2 items).
     * Note: Any added margin to the view will not be counted in the formula
     * @param itemsToFit
     */
    override fun setItemsToFitInScreen(itemsToFit: Float) {
        try {
            val dm = context.resources.displayMetrics
            mItemWidth = dm.widthPixels / itemsToFit
        } catch (e: ArithmeticException) {
            e.printStackTrace()
        }
    }

    /**
     * Set the item width percentage for the screen width
     */
    override fun setItemWidthPercentage(percentage: Float) {
        val dm = context.resources.displayMetrics
        mItemWidth = dm.widthPixels * percentage
    }

    override fun getItemViewType(index: Int): Int {
        return mItems[index].listItemType.mItemViewType
    }

    override val items: MutableList<ListItem>
        get() = mItems

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }

    override fun getItemCount(): Int {
        return mItems.size
    }
}
