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

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.haizo.generaladapter.interfaces.BaseActionCallback
import com.haizo.generaladapter.interfaces.ViewHolderExtras
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ViewHolderContract
import com.haizo.generaladapter.utils.BindingClassMisMatch
import com.haizo.generaladapter.utils.CallbackClassMisMatch
import com.haizo.generaladapter.utils.CastingUtil
import com.haizo.generaladapter.utils.ContractsPool
import com.haizo.generaladapter.utils.Exceptions
import com.haizo.generaladapter.utils.ExtrasClassMisMatch
import com.haizo.generaladapter.utils.UnExpectedException
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder
import com.haizo.generaladapter.viewholders.BlankViewHolder
import kotlin.math.roundToInt

open class BlenderListAdapter constructor(
    private val context: Context,
    vararg actionCallbacks: BaseActionCallback
) : BaseRecyclerListAdapter() {

    private var viewHolderExtrasList: List<ViewHolderExtras> = ArrayList()
    private var actionCallbacks: MutableList<BaseActionCallback> = actionCallbacks.toMutableList()
    private var mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mItemWidth: Float? = null

    fun addActionCallback(listener: BaseActionCallback) {
        actionCallbacks.add(listener)
    }

    fun removeActionCallback(listener: BaseActionCallback) {
        if (actionCallbacks.contains(listener)) {
            actionCallbacks.remove(listener)
        }
    }

    fun setExtraParams(vararg viewHolderExtras: ViewHolderExtras) {
        viewHolderExtrasList = viewHolderExtras.toList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder<ListItem> {
        val viewHolderContract = ContractsPool.getViewHolderContract(viewType)
        val binding = getViewDataBinding(viewHolderContract, parent)
        setupViewWidth(binding)

        val callback: BaseActionCallback? = actionCallbacks.find {
            CastingUtil.castOrNull(it, viewHolderContract.callbackClass) != null
        } ?: actionCallbacks.firstOrNull()

        val viewHolderExtras: ViewHolderExtras? = viewHolderExtrasList.find {
            CastingUtil.castOrNull(it, viewHolderContract.extrasClass) != null
        } ?: viewHolderExtrasList.firstOrNull()

        return constructInstance(viewHolderContract, binding, callback, viewHolderExtras)
    }

    @Suppress("UNCHECKED_CAST")
    private fun constructInstance(
        viewHolderContract: ViewHolderContract,
        binding: ViewDataBinding,
        callback: BaseActionCallback?,
        viewHolderExtras: ViewHolderExtras?
    ): BaseBindingViewHolder<ListItem> {
        val mainConstructor = viewHolderContract.viewHolderClass.constructors[0]
        val constructorSize = mainConstructor.parameterTypes.size

        try {
            return when (constructorSize) {
                1 -> mainConstructor.newInstance(binding)
                2 -> mainConstructor.newInstance(binding, callback)
                3 -> mainConstructor.newInstance(binding, callback, viewHolderExtras)
                else -> BlankViewHolder(binding, callback)
            } as BaseBindingViewHolder<ListItem>
        } catch (e: IllegalArgumentException) {
            when {
                Exceptions.isBindingException(binding, mainConstructor) -> {
                    throw BindingClassMisMatch(e, viewHolderContract)
                }
                Exceptions.isCallbackException(callback, mainConstructor) && constructorSize >= 2 -> {
                    throw CallbackClassMisMatch(e, viewHolderContract)
                }
                Exceptions.isExtrasException(viewHolderExtras, mainConstructor) && constructorSize == 3 -> {
                    throw ExtrasClassMisMatch(e, viewHolderContract)
                }
                else -> throw (UnExpectedException(e, viewHolderContract))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return BlankViewHolder(binding, callback)
    }

    override fun onBindViewHolder(bindingViewHolder: BaseBindingViewHolder<ListItem>, position: Int) {
        bindingViewHolder.draw(currentList[position])
    }

    override fun onViewAttachedToWindow(holderBinding: BaseBindingViewHolder<ListItem>) {
        super.onViewAttachedToWindow(holderBinding)
        holderBinding.onViewAttachedToWindow()
    }

    override fun onViewDetachedFromWindow(holderBinding: BaseBindingViewHolder<ListItem>) {
        holderBinding.onViewDetachedFromWindow()
        super.onViewDetachedFromWindow(holderBinding)
    }

    override fun onViewRecycled(holder: BaseBindingViewHolder<ListItem>) {
        holder.onViewRecycled()
        super.onViewRecycled(holder)
    }

    private fun getViewDataBinding(viewHolderContract: ViewHolderContract, parent: ViewGroup): ViewDataBinding {
        return DataBindingUtil.inflate(mInflater, viewHolderContract.layoutResId, parent, false)
    }

    private fun setupViewWidth(binding: ViewDataBinding) {
        mItemWidth?.let { binding.root.layoutParams.width = (it).roundToInt() }
    }

    /**
     * Set the number of the items that will fit in the screen (Horizontally), so for ex, 1.5 will show 1 and (half item/quarter of 2 items).
     * Note: Any added margin to the view will not be counted in the formula
     * @param itemsToFit
     */
    fun setItemsToFitInScreen(itemsToFit: Float) {
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
    fun setItemWidthPercentage(percentage: Float) {
        val dm = context.resources.displayMetrics
        mItemWidth = dm.widthPixels * percentage
    }
}