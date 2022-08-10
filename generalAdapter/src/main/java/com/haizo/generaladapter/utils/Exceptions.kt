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

import com.haizo.generaladapter.model.ViewHolderContract

private const val MESSAGE_WRONG_2_ARGUMENTS =
    """ 
        ========================================================================
        Issue in ViewHolder: ---> %s <----
        ----------Tips----------
        -> The ViewHolder's constructor should as: (Binding) OR (Binding, BaseActionCallback) OR (Binding, BaseActionCallback, ViewHolderExtras)
        -> Make sure that your ListItem override the correct viewHolderContract
        -> Make sure that you have passed the correct ListItem class type in your (BaseBindingViewHolder<HERE>)
        -> Make sure that you are passing the callback instance to the adapter via constructor or by using addActionCallback(..) 
        -> If you are using your own callback in the ViewHolder, then make sure to define it in its ViewHolderContract
        -> Make sure the you are using the right types as defined in the ViewHolder/ViewHolderContract constructor
        ========================================================================
        """

private const val MESSAGE_WRONG_3_ARGUMENTS =
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

private const val LOAD_MORE_NOT_INITIALIZED =
    "You are trying to use the LoadMore's methods while the LoadMore is not initialized, call setupLoadMore() first before using the methods"

internal class Wrong2ArgumentsParamException(
    e: Exception,
    viewHolderContract: ViewHolderContract
) : IllegalArgumentException(
    String.format(MESSAGE_WRONG_2_ARGUMENTS, viewHolderContract.viewHolderClass, viewHolderContract.itemName), e
)

internal class Wrong3ArgumentsParamException(
    e: Exception,
    viewHolderContract: ViewHolderContract
) : IllegalArgumentException(
    String.format(MESSAGE_WRONG_3_ARGUMENTS, viewHolderContract.viewHolderClass, viewHolderContract.itemName), e
)

class LoadMoreNotInitialized : NullPointerException(LOAD_MORE_NOT_INITIALIZED)