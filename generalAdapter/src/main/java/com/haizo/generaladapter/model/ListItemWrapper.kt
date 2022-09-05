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
package com.haizo.generaladapter.model

/**
 * This could be used as a wrapper class for the model that will be used inside the ViewHolder
 * This may need this when you do not want to let your model implements the ListItem,
 * such as in Clean-Architecture when the model is separated in a differance package as in Domain-Package
 * !!! Important: the class's constructor the implements the ListItemWrapper should only have one param, which is your wrapped model
 */
interface ListItemWrapper : ListItem {

    companion object {

        /**
         * This method allows you to build your ListItemWrapper with different constructor params as you need
         * @param [wrapperInstance] pass an instance of your wrapper item here
         */
        fun <T> List<Any>.wrapWith(wrapperInstance: (Any) -> T): List<T> where T : ListItemWrapper {
            return ArrayList<T>().also { list ->
                forEach { list.add(wrapperInstance.invoke(it)) }
            }.toList()
        }

        /**
         * Helper method to wrap any items with a listItemWrapper
         */
        @Deprecated(
            "This method requests that your ListItemWrapper should have only one parameters and this is a limitation" +
                ", to solve this use the new method above wrapWith()",
            replaceWith = ReplaceWith("ListItemWrapper.wrapWith()")
        )
        inline fun <reified T : ListItemWrapper> wrap(items: List<*>?): List<T> {
            return ArrayList<T>().also { list ->
                items?.forEach { item ->
                    val listItemInstance = T::class.java.constructors.first().newInstance(item) as T
                    list.add(listItemInstance)
                }
            }
        }
    }
}