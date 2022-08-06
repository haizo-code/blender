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

import androidx.databinding.ViewDataBinding
import com.haizo.generaladapter.interfaces.BaseActionCallback
import com.haizo.generaladapter.model.ListItem

class BlankViewHolder constructor(
    binding: ViewDataBinding, actionCallback: BaseActionCallback?
) : BaseBindingViewHolder<ListItem>(binding, actionCallback) {
    override fun onBind(listItem: ListItem) {}
}