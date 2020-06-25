package com.haizo.generaladapter.adapter

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.haizo.generaladapter.loadmore.LoadMoreAdapter
import com.haizo.generaladapter.model.ListItem
import java.util.ArrayList

abstract class BaseRecyclerAdapter<M : ListItem, VH : RecyclerView.ViewHolder>(context: Context?) :
    LoadMoreAdapter<VH>() {

    protected var mItems: ArrayList<M>? = null
    protected var mInflater: LayoutInflater

    init {
        mItems = ArrayList()
        mInflater = LayoutInflater.from(context)
    }

    override fun getItemViewType(position: Int): Int {
        return mItems?.get(position)?.listItemType?.itemViewType ?: 0
    }

    fun add(`object`: M) {
        if (mItems == null) {
            mItems = ArrayList()
        }
        mItems?.add(`object`)
        notifyItemInserted(mItems!!.size - 1)
        notifyDataSetChanged()
    }

    fun add(index: Int, `object`: M) {
        if (mItems == null) {
            mItems = ArrayList()
        }
        mItems?.add(index, `object`)
        notifyItemInserted(index)
        notifyDataSetChanged()
    }

    fun addAll(collection: Collection<M>?) {
        if (collection != null) {
            mItems?.addAll(collection)
        }
    }

    fun addAll(index: Int, collection: Collection<M>?) {
        if (collection != null && index >= 0) {
            mItems?.addAll(index, collection)
        }
    }

    fun addAll(items: List<M>) {
        mItems?.addAll(items)
    }

    operator fun set(position: Int, item: M) {
        if (mItems == null) {
            mItems = ArrayList()
        }
        mItems?.set(position, item)
        notifyItemChanged(position)
    }

    fun updateList(collection: Collection<M>?) {
        mItems?.clear()
        mItems?.addAll(collection ?: ArrayList())
        notifyDataSetChanged()
    }

    fun indexOf(`object`: M): Int {
        return mItems?.indexOf(`object`) ?: 0
    }

    operator fun contains(`object`: M): Boolean {
        return mItems?.contains(`object`) == true
    }

    fun clear() {
        mItems?.clear()
        notifyDataSetChanged()
    }

    fun remove(`object`: M) {
        mItems?.remove(`object`)
    }

    fun remove(position: Int) {
        if (position >= 0 && position < mItems?.size ?: position) {
            mItems?.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
        }
    }

    fun removeItem(position: Int) {
        if (position >= 0 && position < mItems?.size ?: position) {
            mItems?.removeAt(position)
            notifyItemRemoved(position)
            notifyDataSetChanged()
        }
    }

    fun removeAll(items: List<M>) {
        mItems?.removeAll(items)
    }

    fun getItem(position: Int): M? {
        return mItems?.get(position)
    }

    override val items: MutableList<*>?
        get() = mItems

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return mItems?.size ?: 0
    }
}
