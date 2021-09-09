
<p align="center"><img src="/sample.png" width="250" vspace="24"></p>

Recyclerview General Adapter
=================
[![](https://jitpack.io/v/haizo-code/recyclerview-general-adapter.svg)](https://jitpack.io/#haizo-code/recyclerview-general-adapter)

[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)

[![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16)

![License](http://img.shields.io/badge/license-APACHE2-blue.svg)

## You won't have to code any adapter again!
Android library project that intends to simplify the usage of Adapters for recyclerView using **Data Binding**. 

 * **No more adapters to create**

 * **Supports API levels 16+**

 * **Uses ViewDataBinding**

 * **Handles unlimited multiple types automatically**

 * **Handles Callbacks from Viewholder and vice-versa**

 * **Handles Load more automatically**

 * **Handles all the common actions for the recyclerview-adapter and more...**

 * **100% kotlin ~ Compatible with Java**

 * **Easy to use and implement**

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
	implementation 'com.github.haizo-code:recyclerview-general-adapter:v1.5.2'
}
```

## Usage
### Initializing the adapter
Create an instance from GeneralBindingListAdapter and bind it to your recyclerview
```kotlin
private val adapter: GeneralBindingListAdapter by lazy {
    GeneralBindingListAdapter(context = this, actionCallback = this)
}
```

### Bind the adapter to recyclerview
```kotlin
recyclerview?.adapter = adapter
```

### Display the items
Just need to pass your models (ListItems) to the adapter and that's it :) 
  You can mix all the types together and it will be handled automatically by the adapter
```kotlin
val myList = listOf(
    userModel,
    storyModel,
    ...
)
adapter.addAll(myList)
```
------------
------------

## Setup the adpater (3 steps):

### 1. Setup the ViewHolder
Create your **ViewHolder** and extend it with **BaseBindingViewHolder<YourModelHere>** with params:
 * **ViewDataBinding**: Your ViewDataBinding class
 * **BaseActionCallback**: Note that you can add your custom action callback (must implements BaseActionCallback)

```kotlin
class StoryViewHolder(private val viewDataBinding: RowStoryBinding, actionCallback: BaseActionCallback?) :
    BaseBindingViewHolder<StoryModel>(viewDataBinding, actionCallback) {

    override fun onBind(listItem: StoryModel) {
    	// use the listItem here..
    }
}
```

### 2. Add the types for the items
Create a object class **MyListItemTypes** that will be holding the types of the **ViewHolders** that will be used in the Model.
Note that you can create many files like this one, just create instances from ListItemType and pass these params:
 * **ViewHolder**: Your ViewHolder class that will be associated with the ListItemType object
 * **LayoutResId**: Your Layout-Resource-Id that will be associated with the ListItemType object
 * **ItemName** (Optional): This name is not used anywhere but it will be helpful while debugging
 ```kotlin
object MyListItemTypes {

    // These variables will be associated with the listItems
    val ITEM_USER_CARD = ListItemType(
        viewHolderClass = UserCardViewHolder::class.java,
        layoutResId = R.layout.row_user_card,
        itemName = "ITEM_USER_CARD")

    val ITEM_STORY = ListItemType(
        viewHolderClass = StoryViewHolder::class.java,
        layoutResId = R.layout.row_story,
        itemName = "ITEM_STORY")

	...
}
```

### 3. Setup your Models
let your Model **implements ListItem** and override the **ListItemType** variable
```kotlin
class StoryModel(
    val id: String,
    val imageUrl: String
) : ListItem {
    // This model will be presenting the ITEM_STORY
    override var listItemType: ListItemType? = MyListItemTypes.ITEM_STORY
}
```

And thats it :)

-------------
-------------

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

### LoadMore
```kotlin
// Setup the loadmore
adapter.setupLoadMore(loadMoreListener)

// Callback of the loadmore
override fun onLoadMore(pageToLoad: Int) {
    // Your code...
}
```

#### Adding the LoadMore items
You should use this method to add more items to the list when you are using the LoadMore
```kotlin
adapter.addMoreItems(list)
```


### Custom LoadMore behavior
if you want to add your own behavior when loadmore triggers then you can do as below

// Setup the loadmore
 * **loadMoreListener**: Loadmore listener callback
 * **autoShowLoadingItem**: Determine if you want to from the adapter to show a loading item in the list
 * **pageSize**: Page size for the list, this will be used to determine when to call the next page depending on the loading threshold
 * **loadingThreshold**: Depends on the pagesize in backward, ex: threshold = 3 -> triggers loadmore on item 17

```kotlin
adapter.setupLoadMore(loadMoreListener = this, autoShowLoadingItem = false, pageSize = 20, loadingThreshold = 3)

// Callback of the loadmore
override fun onLoadMore(pageToLoad: Int) {
    // Show your own loading (in case autoShowLoadingItem is false)
    // Your code...
}

override fun onLoadMoreFinished() {
    super.onLoadMoreFinished()
    // Hide loading
    // Your code...
}
```

## Advanced

### Sending extra params to the ViewHolder
You can pass any extra param by **vararg** to the ViewHolder using this method:
```kotlin
adapter.setExtraParams(..)
```

### Receiving the extra params in the ViewHolder
You just need to add the extra params in the constructor of the ViewHolder (keep in mind to add the params here in the same sequence you passed it)
```kotlin
class StoryViewHolder(
private val viewDataBinding: RowStoryBinding,
private val actionCallback: BaseActionCallback?,
.. extra params here
..
..
) : BaseBindingViewHolder<StoryModel>(viewDataBinding, actionCallback) 
	...
}
```

### Customize ViewHolder callback
You can add your custom callbacks to the viewholder by:
* **1. Create your interface and let it implements BaseActionCallback**
```kotlin
interface MyActions : BaseActionCallback {
    fun myAction1()
    fun myAction2()
}
```
* **2. Replace the BaseActionCallback with your own interface**
```kotlin
class UserCardViewHolder(private val viewDataBinding: RowUserCardBinding, actionCallback: MyActions?) :
    BaseBindingViewHolder<UserCardModel>(viewDataBinding, actionCallback) {
    ...
}
````
## Adapter Methods

### General methods
| Method | Description |
| ------ | ------ |
| setItemsToFitInScreen | Set the number of the items that will fit in the screen (Horizontally), for ex, 1.5f will show one item and the half of the second item |
| setItemWidthPercentage | Set the item width percentage for the screen width |
| clear() | Clear all the list and reset page number of the LoadMore |
| add(listItem) | Add item to the adapter |
| add(index, listItem) | Add item to the adapter in a specific index |
| addAll(listItems) | Add list of items to the adapter |
| addAll(index, listItems) | Add list of items to the adapter in a specific index |
| set(index, listItem) | Update a specific index |
| updateList(listItems) | Update the whole list with a new list |
| remove(index) | Remove listItem at index |
| remove(listItem) | Remove the passed listItem from the main list if exists |
| removeAll(items) | Remove the passed listItems list from the main list |
| indexOf(listItem) | return index of the passed item (-1 if not found) |
| contains(listItem) | return true if the main list contains a specific listItem |
| getItem(index) | return the Item at index |
| moveItem(from, to) | perform move item from index to index |

### Loadmore methods
| Method | Description |
| ------ | ------ |
| setupLoadMore | Use this method to setup the loadmore for the current recyclerview adapter |
| addMoreItems | Use this method when you add the new loaded items from LoadMore |
| removeLoadMoreIfExists | Remove the loading indicator if exists and triggers the callback for [LoadMoreListener.onLoadMoreFinished] |
| resetPageNumber | Reset the current page number to Page = 1 |
| setCurrentPageNumber | Manual setting the value of the current page number |

    
ProGuard
--------
You need to include the below line in your proguard-rules.pro

```pro
-keepclassmembers public class * extends com.haizo.generaladapter.viewholders.BaseBindingViewHolder{ public protected *; }
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
    
