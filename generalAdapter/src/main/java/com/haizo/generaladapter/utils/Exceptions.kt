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
package com.haizo.generaladapter.utils

import androidx.databinding.ViewDataBinding
import com.haizo.generaladapter.interfaces.BaseActionCallback
import com.haizo.generaladapter.interfaces.ViewHolderExtras
import com.haizo.generaladapter.model.ViewHolderContract
import com.haizo.generaladapter.utils.Exceptions.BINDING_CLASS_MIS_MATCH_MESSAGE
import com.haizo.generaladapter.utils.Exceptions.CALLBACK_CLASS_MIS_MATCH_MESSAGE
import com.haizo.generaladapter.utils.Exceptions.EXTRAS_CLASS_MIS_MATCH_MESSAGE
import com.haizo.generaladapter.utils.Exceptions.LOAD_MORE_NOT_INITIALIZED
import com.haizo.generaladapter.utils.Exceptions.UN_EXPECTED_EXCEPTION_MESSAGE
import java.lang.reflect.Constructor

internal object Exceptions {

    const val LOAD_MORE_NOT_INITIALIZED =
        "You are trying to use the LoadMore's methods while the LoadMore is not initialized, call setupLoadMore() first before using the methods"

    const val BINDING_CLASS_MIS_MATCH_MESSAGE =
        "The Layout resource that is defined in the Contract (%s) does not match the defined Binding class in the ViewHolder %s"

    const val CALLBACK_CLASS_MIS_MATCH_MESSAGE =
        "The Callback class (%s) that is defined in the Contract (%s) does not match the defined in the ViewHolder of %s)"

    const val EXTRAS_CLASS_MIS_MATCH_MESSAGE =
        "The Extras class (%s) that is defined in the Contract (%s) does not match the defined in the ViewHolder of %s)"

    const val UN_EXPECTED_EXCEPTION_MESSAGE =
        """ 
        ========================================================================
        Issue in ViewHolder: ---> %s <----
        ----------Tips----------
        -> The ViewHolder's constructor should as: (Binding) OR (Binding, BaseActionCallback) OR (Binding, BaseActionCallback, ViewHolderExtras)
        -> Make sure that your ListItem override the correct viewHolderContract
        -> Make sure that you have passed the correct ListItem/ListItemWrapper class type in your (BaseBindingViewHolder<HERE>)
        -> Make sure that you are passing the (callback/extras) instance/s to the adapter.
        -> If you are using your own callback in the ViewHolder, then make sure to define it also in the ViewHolderContract
        -> If you are receiving ViewHolderExtras in the ViewHolder, then make sure to define it also in the ViewHolderContract
        -> Make sure the you are using the right types as defined in the ViewHolder/ViewHolderContract constructor
        ========================================================================
        """

    fun isBindingException(binding: ViewDataBinding, mainConstructor: Constructor<*>): Boolean {
        return CastingUtil.castOrNull(binding, mainConstructor.parameterTypes[0]) == null
    }

    fun isCallbackException(callback: BaseActionCallback?, mainConstructor: Constructor<*>): Boolean {
        if (mainConstructor.parameterTypes.size >= 2) {
            return CastingUtil.castOrNull(callback, mainConstructor.parameterTypes[1]) == null
        }
        return false
    }

    fun isExtrasException(extras: ViewHolderExtras?, mainConstructor: Constructor<*>): Boolean {
        if (mainConstructor.parameterTypes.size >= 3) {
            return CastingUtil.castOrNull(extras, mainConstructor.parameterTypes[2]) == null
        }
        return false
    }
}

internal class UnExpectedException(
    e: Exception,
    viewHolderContract: ViewHolderContract
) : IllegalArgumentException(
    String.format(UN_EXPECTED_EXCEPTION_MESSAGE, viewHolderContract.viewHolderClass, viewHolderContract.itemName), e
)

internal class LoadMoreNotInitialized : NullPointerException(LOAD_MORE_NOT_INITIALIZED)

internal class BindingClassMisMatch(e: Exception, viewHolderContract: ViewHolderContract) : IllegalArgumentException(
    String.format(
        BINDING_CLASS_MIS_MATCH_MESSAGE,
        viewHolderContract.itemName,
        viewHolderContract.viewHolderClass.simpleName
    ), e
)

internal class CallbackClassMisMatch(e: Exception, viewHolderContract: ViewHolderContract) : IllegalArgumentException(
    String.format(
        CALLBACK_CLASS_MIS_MATCH_MESSAGE,
        viewHolderContract.callbackClass?.simpleName,
        viewHolderContract.itemName,
        viewHolderContract.viewHolderClass.simpleName
    ), e
)

internal class ExtrasClassMisMatch(e: Exception, viewHolderContract: ViewHolderContract) : IllegalArgumentException(
    String.format(
        EXTRAS_CLASS_MIS_MATCH_MESSAGE,
        viewHolderContract.extrasClass?.simpleName,
        viewHolderContract.itemName,
        viewHolderContract.viewHolderClass.simpleName
    ), e
)