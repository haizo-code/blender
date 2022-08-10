
<img width="800" alt="blender_adapter" src="https://user-images.githubusercontent.com/17477070/145425168-f01c4af1-b0c4-4de5-b72c-ca6067bd7431.png">

Recyclerview Smart Adapter
=================
[![](https://jitpack.io/v/haizo-code/recyclerview-general-adapter.svg)](https://jitpack.io/#haizo-code/recyclerview-general-adapter)
[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
[![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16)
![License](http://img.shields.io/badge/license-APACHE2-blue.svg)

## You won't have to code any adapter again!
Android library project that intends to simplify the usage of Adapters for recyclerView using **Data Binding**.

* No more adapters to create
* Supports (RecyclerView adapter) and (RecyclerView ListAdapter + DiffUtil)
* Supports API levels 19+
* Uses ViewDataBinding
* Handles unlimited multiple types automatically
* Handles Callbacks from ViewHolder and vice-versa
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
	implementation 'com.github.haizo-code:recyclerview-general-adapter:v2.2.1'
}
```

## Usage
### Initializing the adapter
Create an instance from BlenderListAdapter and bind it to your recyclerview
```kotlin
private val adapter: BlenderListAdapter by lazy {
  BlenderListAdapter(context = this, actionCallbacks = this)
}
```

```kotlin
recyclerview.adapter = adapter
```
### Display the items
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
------------
------------

## Setup the adapter (3 steps):

### 1. Setup the ViewHolder
Create your **ViewHolder** and extend it with **BaseBindingViewHolder<YourModelHere>** with params:
* **ViewDataBinding**: Your ViewDataBinding class
* **BaseActionCallback**: Note that you can add your custom action callback (must implements BaseActionCallback)

```kotlin
@file:Suppress("CanBeParameter")

class StoryViewHolder(private val binding: RowStoryBinding, actionCallback: BaseActionCallback?) :
    BaseBindingViewHolder<StoryModel>(binding, actionCallback) {

    override fun onBind(listItem: StoryModel) {
        // use the listItem here..
    }
}
```

### 2. Add the types for the items
Create a object class **MyListItemTypes** that will be holding the types of the **ViewHolders** that will be used in the Model.
Note that you can create many files like this one, just create instances from ViewHolderContract and pass these params:
* **ViewHolder**: Your ViewHolder class that will be associated with the ViewHolderContract object
* **LayoutResId**: Your Layout-Resource-Id that will be associated with the ViewHolderContract object
* **ItemName** (Optional): This name is not used anywhere but it will be helpful while debugging
 ```kotlin
object MyListItemTypes {

  // These variables will be associated with the listItems
  val ITEM_USER_CARD = ViewHolderContract(
          viewHolderClass = UserCardViewHolder::class.java,
          layoutResId = R.layout.row_user_card,
          itemName = "ITEM_USER_CARD")

  val ITEM_STORY = ViewHolderContract(
          viewHolderClass = StoryViewHolder::class.java,
          layoutResId = R.layout.row_story,
          itemName = "ITEM_STORY")

  ...
}
```

### 3. Setup your Models
**Option 1.** let your Model **implements ListItem** and override the **ViewHolderContract** variable
```kotlin
class StoryModel(
        val id: String,
        val imageUrl: String
) : ListItem {
  // This model will be presenting the ITEM_STORY
  override var viewHolderContract: ViewHolderContract = MyListItemTypes.ITEM_STORY
}
```

**Option 2.** if you do not want to let your model implements the **ListItem** directly, then you can use the **ListItemWrapper**
Check the **ListItem Wrapper** section below: [Here](#listitem-wrapper)


And that's it :)

-------------

### List Adapter DiffUtil
* if you are using the **BlenderListAdapter**, then you need to override these methods in your ListItem class to be used in the DiffUtil:
```kotlin
data class Story(val id: String) : ListItem {
  ...

  override fun itemUniqueIdentifier(): String {
    return id
  }
  override fun areContentsTheSame(newItem: ListItem): Boolean {
    return if (newItem is Story) {
      this == newItem
    } else false
  }
}
```
* if you are using the **BlenderAdapter** then no need to override the above methods
-------

### LoadMore
* for default usage, you can use this method as:
```kotlin
adapter.setupLoadMore(object : LoadMoreListener {
  override fun onLoadMore(nextPageNumber: Int, nextPageUrl: String?) {
    // request load next page
  }
})
```
* for custom load-more, you can use this method as:
```kotlin
adapter.setupLoadMore(
        pageSize = 10,
        loadingThreshold = 3,
        autoShowLoadingItem = false,
        loadMoreListener = object : LoadMoreListener {
          override fun onLoadMore(pageToLoad: Int) {
            // show your loading
            // request load next page
          }

          override fun onLoadMoreFinished() {
            super.onLoadMoreFinished()
            // hide your loading
          }
        })
```

### Submit Items with LoadMore
#### * Submit Items
Use this method to submit your first page or to update the list with the new items:
```kotlin
// this method will reset the page number and will update the current list with the new list
adapter.submitListItems(list)
```

#### * Submit More Items
Use this method to submit more items to the current list
```kotlin
adapter.submitMoreListItems(list)
// or
adapter.submitMoreListItems(list = list, nextPageUrl = "your-next-page-url-here")

// if you are using the BlenderAdapter (legacy adapter), then you should used this method
// adapter.addMoreItems(list)
```

### * Custom Loading Item
You can use your own loading-item to be shown when loadmore triggered

**Step 1.** create your Loading-model (the same as you create a normal ListItem) but let it implements "LoadingListItem" instead of "ListItem"
```kotlin

// Item
class MyCustomLoadListItem : LoadingListItem {
    override var viewHolderContract: ViewHolderContract = ..
    override fun areContentsTheSame(newItem: ListItem): Boolean = true
}
	
// ViewHolder
class MyLoadingViewHolder constructor(
    binding: RowMyLoadingBinding, actionCallback: UserActionCallback?
) : BaseBindingViewHolder<MyLoadListItem>(binding, actionCallback) {
	..
}

// Item Type
val ITEM_CUSTOM_LOADING = ViewHolderContract(
    viewHolderClass = MyLoadingViewHolder::class.java,
    layoutResId = R.layout.row_my_loading)
```

**Step 2.** pass your created Loading-model to the adapter
```kotlin
adapter.setLoadingListItem(MyCustomLoadListItem())
```
------

### Sending extra params to the ViewHolder

**Step 1.** create a class and let it implements the ViewHolderExtras:
```kotlin
class MySampleExtras : ViewHolderExtras {..}
```

**Step 2.** send this extra to the adapter using this method:
```kotlin
adapter.setExtraParams(mySampleExtras)
```

**Step 3.** define this extra class in the ViewHolderContract initialization:
```kotlin
val ITEM_STORY = ViewHolderContract(
        viewHolderClass = StoryViewHolder::class.java,
        layoutResId = R.layout.row_story,
        itemName = "ITEM_STORY",
        callbackClass = null,
        extrasClass = MySampleExtras::class.java
)
```
**Step 4.** Add the extra param in the constructor of the ViewHolder **as the third param**:
```kotlin
class StoryViewHolder(
        private val viewDataBinding: RowStoryBinding,
        private val actionCallback: BaseActionCallback?,
        private val mySampleExtras: MySampleExtras
) : BaseBindingViewHolder<StoryModel>(viewDataBinding, actionCallback)
...
}
```
------

### Custom callback
You can add your custom callbacks to the ViewHolder by:

**Step 1.** Create your interface and let it implements BaseActionCallback**
```kotlin
interface UserActionCallback : BaseActionCallback {
  fun onAvatarClicked(user: User)
  fun onCallClicked(user: User)
}
```
**Step 2.** Replace the BaseActionCallback with your own interface**
```kotlin
class UserViewHolder constructor(
        private val binding: RowUserCardBinding,
        actionCallback: UserActionCallback?
) : BaseBindingViewHolder<User>(binding, actionCallback) {
  ...
}
````
**Step 3.** define this extra class in the ViewHolderContract initialization:
```kotlin
   val ITEM_USER_CARD = ViewHolderContract(
        viewHolderClass = UserViewHolder::class.java,
        layoutResId = R.layout.row_user_card,
        itemName = "ITEM_USER_CARD",
        UserActionCallback::class.java)
````

------

### ListItem Wrapper
for example, if you are using clean architecture and you don't want to let your model in the Domain-Module implements the ListItem directly,
then you can use the **ListItemWrapper** to solve it, just create a new class that implements this wrapper and add your model in it:
example:

```kotlin
class StoryListItemWrapper constructor(val story: StoryModel) : ListItemWrapper {
  override var viewHolderContract: ViewHolderContract = MyListItemTypes.ITEM_STORY
}
```
and update the ViewHolder to use the wrapper instead of the direct model as:
```kotlin
BaseBindingViewHolder<StoryListItemWrapper>(viewDataBinding, actionCallback)
```
finally, update the adapter with the list of wrappers, you can use the below helper method inside ListItemWrapper.kt:
```kotlin
val list : list<StoryModel> = ...
adapter.submitList(ListItemWrapper.wrap<StoryListItemWrapper>(list))
```
------

### Item click callback (From ViewHolder and vice-versa)
This callback will be triggered from the ViewHolder, also you can trigger backward
callback to the ViewHolder using the **BackwardActionCallback** interface
```kotlin
override fun onItemClicked(view: View, listItem: ListItem, position: Int, bwCallback: BackwardActionCallback) {
  when (listItem) {
    is UserCardModel -> toast(listItem.text)
    is StoryViewHolder -> toast(listItem.imageUrl)
      ...
    // You can use the Backward-Action here as:
    // bwCallback.onBackwardAction(Args if exists)
  }
}
```

### Handle Backward Callback
You just need to override this method in your ViewHolder and you will be receiving the callbacks here.
```kotlin
override fun onBackwardAction(vararg args: Any) {
  super.onBackwardAction(*args)
  // Your code to handle the backward action/s ...
}
```
------

### Adapter extra methods

| Method | Description |
| ------ | ------ |
| setItemsToFitInScreen | Set the number of the items that will fit in the screen (Horizontally), for ex, 1.5f will show one item and the half of the second item |
| setItemWidthPercentage | Set the item width percentage for the screen width |


ProGuard
--------
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
    
