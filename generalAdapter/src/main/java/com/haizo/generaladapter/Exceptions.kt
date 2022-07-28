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
package com.haizo.generaladapter

import com.haizo.generaladapter.model.ListItemType

private const val MESSAGE_WRONG_2_ARGUMENTS =
    """ 
        ========================================================================
        Issue in ViewHolder: ---> %s <----
        --------------------
        -> Make sure to specify the callback in the listItemType's (%s) constructor under param: 'callbackClass'
        -> Make sure that you are passing the callback instance to the adapter.
        -> Make sure the you are using the right type as defined in the ViewHolder/ListItemType constructor
        ========================================================================
        """

private const val MESSAGE_WRONG_3_ARGUMENTS =
    """ 
        ========================================================================
        Issue in ViewHolder: ---> %s <----
        --------------------
        -> Make sure to specify the Callback/Extra-Params in the listItemType's (%s) constructor under param: 'callbackClass/extras'
        -> Make sure that you are passing the (callback/extras) instance/s to the adapter.
        -> Make sure the you are using the right types as defined in the ViewHolder/ListItemType constructor
        ========================================================================
        """

private const val LOAD_MORE_NOT_INITIALIZED =
    "You are trying to use the LoadMore's methods while the LoadMore is not initialized, call setupLoadMore() first before using the methods"

class Wrong2ArgumentsParamException(
    e: Exception,
    listItemType: ListItemType
) : IllegalArgumentException(
    String.format(MESSAGE_WRONG_2_ARGUMENTS, listItemType.viewHolderClass, listItemType.itemName), e)

class Wrong3ArgumentsParamException(
    e: Exception,
    listItemType: ListItemType
) : IllegalArgumentException(
    String.format(MESSAGE_WRONG_3_ARGUMENTS, listItemType.viewHolderClass, listItemType.itemName), e)

class LoadMoreNotInitialized : NullPointerException(LOAD_MORE_NOT_INITIALIZED)