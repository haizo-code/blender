<img width="800" alt="blender_adapter" src="https://user-images.githubusercontent.com/17477070/145425168-f01c4af1-b0c4-4de5-b72c-ca6067bd7431.png">

Recyclerview Smart Adapter
=================
[![](https://jitpack.io/v/haizo-code/recyclerview-general-adapter.svg)](https://jitpack.io/#haizo-code/recyclerview-general-adapter)
[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
[![API](https://img.shields.io/badge/API-19%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=19)
![License](http://img.shields.io/badge/license-APACHE2-blue.svg)

## You won't have to code any adapter again!

Android library project that intends to simplify the usage of Adapters for recyclerView using **Data Binding**.

* No more adapters to create
* Supports (RecyclerView adapter) and (RecyclerView ListAdapter + DiffUtil)
* Supports API levels 19+
* Uses ViewDataBinding
* Handles unlimited multiple types automatically
* Handles Load more automatically
* Handles all the common actions for the recyclerview-adapter and more...
* 100% kotlin ~ Compatible with Java
* Easy to use and implement

## Gradle

**Step 1.** Add the JitPack repository to your root build.gradle at the end of repositories:

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

**Step 2.** Add the library dependency to your project build.gradle:

```gradle
dependencies {
	implementation 'com.github.haizo-code:recyclerview-general-adapter:v2.3.6'
}
```

## Usage

### Initializing the adapter

Create an instance from BlenderListAdapter and bind it to your recyclerview

```kotlin
private val adapter: BlenderListAdapter by lazy {
  BlenderListAdapter(context = this)
}
```

```kotlin
recyclerview.adapter = adapter
```

## Display the items

Just need to pass your models (ListItems) to the adapter and that's it :)
You can mix all the types together and it will be handled automatically by the adapter

* for **BlenderListAdapter** (RecyclerView ListAdapter - diffUtil) use:

```kotlin
adapter.submitList(list)
```

* for **BlenderAdapter** (Legacy recyclerView adapter) use:

```kotlin
adapter.updateList(list)
```

## Setup the adapter (3 steps):

<img width="800" alt="blender_adapter" src="https://raw.githubusercontent.com/haizo-code/blender/master/blender_adapter_chart.png">

### 1. Create a ViewHolder

Create a **ViewHolder** and let it extends the **BaseBindingViewHolder<MODEL-HERE>**, params as:

* **ViewDataBinding**: Your ViewDataBinding class
* **BaseActionCallback**: Note that you can add your custom action callback (must implements BaseActionCallback)

```kotlin
class UserViewHolder(
    private val binding: RowUserBinding,
    actionCallback: BaseActionCallback?
) : BaseBindingViewHolder<User>(binding, actionCallback) {

    override fun onBind(listItem: User) {
        // use the listItem here..
    }
}
```

### 2. Setup a Contract

Create a new instance from **ViewHolderContract**, expected Params:

* **viewHolderClass**: The ViewHolder Class
* **layoutResId**: The layout resource-id
* **itemName** (Optional): A readable name used for debug only
* **callbackClass** (Optional):
* **extrasClass** (Optional):

 ```kotlin
val USER_VIEW_HOLDER_CONTRACT = ViewHolderContract(
    viewHolderClass = UserViewHolder::class.java,
    layoutResId = R.layout.row_user
)
```

### 3. Register Model with Contract

### Option 1. (Direct)

let your model directly implements **ListItem** and override the **ViewHolderContract** variable

```kotlin
data class User(
    val id: String,
    val Name: String
) : ListItem {
    override var viewHolderContract: ViewHolderContract = USER_VIEW_HOLDER_CONTRACT
}
```

----

### Option 2 (InDirect)

if you do not want to let your model implements the **ListItem** directly, then you can use the **ListItemWrapper**:

**Step 1.** Create a wrapper class that will hold your model in it and let it implement **ListItemWrapper**

```kotlin
class UserWrapper constructor(val user: User) : ListItemWrapper {
    override var viewHolderContract: ViewHolderContract = USER_VIEW_HOLDER_CONTRACT
}
```

**Step 2.**
Update the ViewHolder to use the wrapper instead of the direct model as:

```kotlin
BaseBindingViewHolder<UserWrapper>(viewDataBinding, actionCallback)
```

**Step 3.** finally, update the adapter with the list of wrappers, you can use the below helper method inside
ListItemWrapper.kt:

```kotlin
val usersList: list<User> = YOUR-USERS-LIST
val wrappedUsersList = usersList.map { UserWrapper(it) }
adapter.submitList(wrappedUsersList)
```

And that's it :)

## List Adapter DiffUtil

* if you are using the **BlenderListAdapter**, then you need to override these methods in your ListItem class to be used
  in the DiffUtil:

```kotlin
data class User(
    val id: String
) : ListItem {

    override fun itemUniqueIdentifier(): String {
        return id
    }
    override fun areContentsTheSame(newItem: ListItem): Boolean {
        return this == newItem
    }
}
```

* if you are using the **BlenderAdapter**, then its not supported, its recommended to switch to **BlenderListAdapter**

## LoadMore

* for default usage, you can use this method as:

```kotlin
adapter.setupLoadMore(
    loadMoreListener = object : LoadMoreListener {
        override fun onLoadMore(nextPageNumber: Int, nextPagePayload: String?) {
            // request load next page
        }
    })
```

* for custom load-more, you can use this method as:

```kotlin
adapter.setupLoadMore(
    autoShowLoadingItem = true,
    pageSize = 10,
    loadingThreshold = 3,
    loadMoreListener = object : LoadMoreListener {
        override fun onLoadMore(nextPageNumber: Int, nextPagePayload: String?) {
            // request load next page
        }

        override fun isShouldTriggerLoadMore(nextPageNumber: Int, nextPagePayload: String?): Boolean {
            // You can control here if the loadMore should be triggered or not
        }
    })
```

### Submit Items with LoadMore

#### Submit Items

Use this method to submit your first page or to update the list with the new items:

```kotlin
// this method will reset the page number and will update the current list with the new list
adapter.submitListItems(list)
```

#### Submit More Items

Use this method to submit more items to the current list

```kotlin
adapter.submitMoreListItems(list)
// or
adapter.submitMoreListItems(list = list, nextPagePayload = "your-next-page-url-here")

// you can also send the next page url by using this method
// adapter.setNextPagePayload("your-next-page-url-here")

// ### if you are using the BlenderAdapter (legacy adapter), then you should used this method
// adapter.addMoreItems(list)
```

### Custom Loading Item

You can use your own loading-item to be shown when load-More triggered

**Step 1.** create your Loading-model (the same as you create a normal ListItem) but let it implements "LoadingListItem"
instead of "ListItem"

```kotlin
// ViewHolder
class MyLoadingViewHolder constructor(
    private val binding: RowMyLoadingBinding
) : BaseBindingViewHolder<MyLoadListItem>(binding) {
    ...
}

// Contract
val MY_LOADING_VIEW_HOLDER_CONTRACT = ViewHolderContract(
    viewHolderClass = MyLoadingViewHolder::class.java,
    layoutResId = R.layout.row_my_loading
)

// ListItem
class MyCustomLoadListItem : LoadingListItem {
    override var viewHolderContract: ViewHolderContract = MY_LOADING_VIEW_HOLDER_CONTRACT
    override fun areContentsTheSame(newItem: ListItem): Boolean = true
}
```

**Step 2.** pass your created Loading-model to the adapter

```kotlin
adapter.setLoadingListItem(MyCustomLoadListItem())
```

## Sending extra params to the ViewHolder

**Step 1.** create a class and let it implements the ViewHolderExtras:

```kotlin
class FooExtras : ViewHolderExtras {.. }
```

**Step 2.** send instance from the ViewHolderExtras that you have created to the adapter using this method:

```kotlin
adapter.setExtraParams(fooExtras)
```

**Step 3.** define this extra class in the ViewHolderContract initialization:

```kotlin
val USER_VIEW_HOLDER_CONTRACT = ViewHolderContract(
    viewHolderClass = UserViewHolder::class.java,
    layoutResId = R.layout.row_user,
    extrasClass = FooExtras::class.java < ---Here
)
```

**Step 4.** Add the extra param in the constructor of the ViewHolder **as the third param**:

```kotlin
class UserViewHolder(
    private val viewDataBinding: RowUserBinding,
    private val actionCallback: BaseActionCallback?,
    private val fooExtras: FooExtras
) : BaseBindingViewHolder<UserWrapper>(viewDataBinding, actionCallback)
...
}
```

## Item click callback

#### First, register for the callback listener in either ways:

**1.** In the initialization of the adapter:

```kotlin
private val adapter: BlenderListAdapter by lazy {
    BlenderListAdapter(context = this, actionCallbacks = this) // <--- Here, you can pass many different callbacks 
}
```

**2.** by using this method:

```kotlin
adapter.addActionCallback(..)
```

#### Second, trigger the callback from your ViewHolder

```kotlin
// In your ViewHolder
init {
    attachClickListener(itemView)
    // or you can call it manually as
    // actionCallback.onItemClicked(...)

}
```

```kotlin
// In your fragment/activity that implements this callback
override fun onItemClicked(view: View, listItem: ListItem, position: Int) {
    if (listItem is UserWrapper) {
        // do your work here
    }
}
```

## Custom callback

You can pass your custom callbacks to the ViewHolder by:

**Step 1.** Create your interface and let it implements BaseActionCallback**

```kotlin
interface UserActionCallback : BaseActionCallback {
    fun foo(user: User)
    fun bar(user: User)
}
```

**Step 2.** Replace the BaseActionCallback with your own interface**

```kotlin
class UserViewHolder constructor(
    private val binding: RowUserCardBinding,
    private val actionCallback: UserActionCallback?
) : BaseBindingViewHolder<User>(binding, actionCallback) {
    ...
}
````

**Step 3.** define this extra class in the ViewHolderContract initialization:

```kotlin

val USER_VIEW_HOLDER_CONTRACT = ViewHolderContract(
    viewHolderClass = UserViewHolder::class.java,
    layoutResId = R.layout.row_user,
    extrasClass = FooExtras::class.java,
    callbackClass = UserActionCallback::class.java < ----Here
)
````

## Adapter extra methods

| Method | Description |
| ------ | ------ |
| setItemsToFitInScreen(..) | Set the number of the items that will fit in the screen (Horizontally), for ex, 1.5f will show one item and the half of the second item |
| setItemWidthPercentage(..) | Set the item width percentage for the screen width |
| removeItemFromList(..) | Remove item from the main list and resubmit the list internally |
| addItemToList(..) | Add item into the main list and resubmit the list internally |		     
| updateItemData(..) | Used when the item has been updated by reference, so in this case the DiffUtil will not see the change |
| indexOf(..) | Find the index of item, can be used by ListItem object or Item-Unique_Identifier |

## ProGuard
You need to include the below line in your proguard-rules.pro

```pro
-keepclassmembers public class * extends com.haizo.generaladapter.viewholders.BaseBindingViewHolder{ public protected *; }
-keepclassmembers public class * extends com.haizo.generaladapter.model.**{ public protected *; }
```

# License

    Copyright 2020 Farouq Afghani

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    
