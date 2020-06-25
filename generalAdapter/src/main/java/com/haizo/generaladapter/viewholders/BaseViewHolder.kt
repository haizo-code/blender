package com.haizo.generaladapter.viewholders

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.ListItemCallback

/*
* Read this before creating any view-holder
* https://kotlinlang.org/docs/tutorials/android-plugin.html
* https://proandroiddev.com/kotlin-android-extensions-using-view-binding-the-right-way-707cd0c9e648
* */

open class BaseViewHolder<T: ListItem>(binding: ViewDataBinding, private val mCallback: ListItemCallback? = null) :
    RecyclerView.ViewHolder(binding.root), View.OnClickListener {
    private lateinit var mListItem: T

    private val mPosition: Int
        get() = adapterPosition

    protected val context: Context
        get() = itemView.context

    open fun draw(listItem: T) {
        mListItem = listItem
    }

    protected fun attachClickListener(vararg views: View?) {
        for (view in views) {
            view?.setOnClickListener(this)
        }
    }

    /*** Special Adapter Callbacks  */

    fun onViewAttachedToWindow() {}

    fun onViewDetachedFromWindow() {
        itemView.clearAnimation()
    }

    override fun onClick(view: View) {
        mCallback?.onItemClicked(view, mListItem, mPosition)
    }
}