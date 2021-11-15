package com.haizo.generaladapter.interfaces

import android.content.Context

interface AdapterActions<M> {

    /**
     * Clear all the list and reset page number of the LoadMore
     */
    fun clear()

    /**
     * Add item to the adapter
     * @param listItem
     */
    fun add(listItem: M)

    /**
     * Add item to the adapter in a specific index
     * @param index
     * @param listItem
     */
    fun add(index: Int, listItem: M)

    /**
     * Add list of items to the adapter
     * @param listItems
     */
    fun addAll(listItems: List<M>)

    /**
     * Add list of items to the adapter in a specific index
     * @param index
     * @param listItems
     */
    fun addAll(index: Int, listItems: List<M>)

    /**
     * Update a specific index
     * @param index
     * @param listItem
     */
    operator fun set(index: Int, listItem: M)

    /**
     * Update the whole list with a new list
     * @param listItems
     */
    fun updateList(listItems: List<M>?)

    /**
     * Remove listItem at index
     * @param [index]
     */
    fun remove(index: Int)

    /**
     * Remove the passed listItem from the main list
     * @param [listItem]
     */
    fun remove(listItem: M)

    /**
     * Remove the passed listItems list from the main list
     * @param [items]: ListItems
     */
    fun removeAll(items: List<M>)

    /**
     * @return index of the passed item (-1 if not found)
     * @param [listItem]
     */
    fun indexOf(listItem: M): Int

    /**
     * @return true if the main list contains a specific listItem
     * @param [listItem]
     */
    fun contains(listItem: M): Boolean

    /**
     * perform move item from index to index
     * @param from
     * @param to
     */
    fun moveItem(from: Int, to: Int): Boolean

    /**
     * return the Item at index
     * @param [index]
     */
    fun getItem(index: Int): M?

    /**
     * Set the number of the items that will fit in the screen (Horizontally), so for ex, 1.5 will show 1 and (half item/quarter of 2 items).
     * Note: Any added margin to the view will not be counted in the formula
     * @param itemsToFit
     */
    fun setItemsToFitInScreen(itemsToFit: Float)

    /**
     * Set the item width percentage for the screen width
     */
    fun setItemWidthPercentage(percentage: Float)

}