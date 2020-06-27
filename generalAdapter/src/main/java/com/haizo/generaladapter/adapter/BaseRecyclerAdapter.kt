package com.haizo.generaladapter.adapter

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.haizo.generaladapter.loadmore.LoadMoreAdapter
import com.haizo.generaladapter.model.ListItem
import java.util.ArrayList

abstract class BaseRecyclerAdapter<M : ListItem, VH : RecyclerView.ViewHolder>(context: Context?) :
    LoadMoreAdapter<VH>() {

    protected var mItems: ArrayList<M> = ArrayList()
    protected var mInflater: LayoutInflater = LayoutInflater.from(context)
    protected var itemWidth: Float? = null

    /**
     * Clear all the list
     */
    fun clear() {
        mItems.clear()
        notifyDataSetChanged()
        resetPageNumber()
    }

    /**
     * Add item to the adapter
     * @param listItem
     */
    fun add(listItem: M) {
        mItems.add(listItem)
        notifyItemInserted(mItems.size - 1)
    }

    /**
     * Add item to the adapter in a specific index
     * @param index
     * @param listItem
     */
    fun add(index: Int, listItem: M) {
        if (index in 0 until mItems.size) {
            mItems.add(index, listItem)
            notifyItemInserted(index)
        } else add(listItem)
    }

    /**
     * Add list of items to the adapter
     * @param listItems
     */
    fun addAll(listItems: List<M>) {
        mItems.addAll(listItems)
        notifyItemRangeInserted(mItems.size, mItems.size)
    }

    /**
     * Add list of items to the adapter in a specific index
     * @param index
     * @param listItems
     */
    fun addAll(index: Int, listItems: List<M>) {
        if (index in 0 until mItems.size) {
            mItems.addAll(index, listItems)
            notifyItemRangeInserted(index, listItems.size)
        }else addAll(listItems)
    }

    /**
     * Update a specific index
     * @param index
     * @param listItem
     */
    operator fun set(index: Int, listItem: M) {
        if (index in 0 until mItems.size){
            mItems[index] = listItem
            notifyItemChanged(index)
        }
    }

    /**
     * Update the whole list with a new list
     * @param listItems
     */
    fun updateList(listItems: List<M>?) {
        clear()
        mItems.addAll(listItems ?: ArrayList())
        notifyDataSetChanged()
    }

    /**
     * Remove listItem at index
     * @param [index]
     */
    fun remove(index: Int) {
        if (index in 0 until mItems.size) {
            mItems.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    /**
     * Remove the passed listItem from the main list
     * @param [listItem]
     */
    fun remove(listItem: M) {
        val indexOfItem = indexOf(listItem)
        if (indexOfItem != -1) {
            mItems.remove(listItem)
            notifyItemRemoved(indexOfItem)
        }
    }

    /**
     * Remove the passed listItems list from the main list
     * @param [items]: ListItems
     * TODO: Enhance: in case the list spreads in the main list (not ordered)
     */
    fun removeAll(items: List<M>) {
        if (items.isNotEmpty()) {
//            val startIndex = mItems.indexOf(items[0])
//            if (startIndex != -1){
//                mItems.removeAll(items)
//                notifyItemRangeRemoved(startIndex, items.count())
//            }
            mItems.removeAll(items)
            notifyDataSetChanged()
        }
    }

    /**
     * @return index of the passed item (-1 if not found)
     * @param [listItem]
     */
    fun indexOf(listItem: M): Int {
        return mItems.indexOf(listItem)
    }

    /**
     * @return true if the main list contains a specific listItem
     * @param [listItem]
     */
    operator fun contains(listItem: M): Boolean {
        return mItems.contains(listItem)
    }

    /**
     * return the Item at index
     * @param [index]
     */
    fun getItem(index: Int): M? {
        return mItems[index]
    }

    /**
     * Set the number of the items that will fit in the screen (Horizontally), so for ex, 1.5 will show 1 and (half item/quarter of 2 items).
     * Note: Any added margin to the view will not be counted in the formula
     * @param context
     * @param itemsToFit
     */
    fun setItemsToFitInScreen(context: Context, itemsToFit: Float) {
        try {
            val dm = context.resources.displayMetrics
            itemWidth = dm.widthPixels / itemsToFit
        } catch (e: ArithmeticException) {
            e.printStackTrace()
        }
    }

    /**
     * Set the item width percentage for the screen width
     */
    fun setItemWidthPercentage(context: Context, percentage: Float) {
        val dm = context.resources.displayMetrics
        itemWidth = dm.widthPixels * percentage
    }

    override fun getItemViewType(index: Int): Int {
        return mItems[index].listItemType?.itemViewType ?: 0
    }

    override val items: MutableList<*>
        get() = mItems

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }

    override fun getItemCount(): Int {
        return mItems.size
    }
}
