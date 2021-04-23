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
package com.haizo.generaladapter.viewholders

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.haizo.generaladapter.interfaces.BackwardActionCallback
import com.haizo.generaladapter.interfaces.BaseActionCallback
import com.haizo.generaladapter.interfaces.RecyclerViewAdapterCallback
import com.haizo.generaladapter.model.ListItem

abstract class BaseBindingViewHolder<T : ListItem>(binding: ViewDataBinding,
    private val actionCallback: BaseActionCallback?) :
    RecyclerView.ViewHolder(binding.root), View.OnClickListener, BackwardActionCallback, RecyclerViewAdapterCallback {

    protected lateinit var listItem: T

    protected val context: Context
        get() = itemView.context

    protected fun attachClickListener(vararg views: View?) {
        for (view in views) {
            view?.setOnClickListener(this)
        }
    }

    abstract fun onBind(listItem: T)

    override fun onViewDetachedFromWindow() {
        itemView.clearAnimation()
    }

    fun draw(listItem: T) {
        this.listItem = listItem
        onBind(listItem)
    }

    override fun onClick(view: View) {
        actionCallback?.onItemClicked(view, listItem, bindingAdapterPosition, this)
    }
}