<p align="center"><img src="/sample.png" width="250" align="right" vspace="24"></p>

Recyclerview General Adapter
=================
[![](https://jitpack.io/v/Haizo94/recyclerview-general-adapter.svg)](https://jitpack.io/#Haizo94/recyclerview-general-adapter)
[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
[![API](https://img.shields.io/badge/API-19%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=19)
![License](http://img.shields.io/badge/license-APACHE2-blue.svg)

Android library project that intends to simplify the usage of Adapters for recyclerView using **Data Binding**. You won't have to code any adapter again!

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
	implementation 'com.github.haizo-code:recyclerview-general-adapter:v1.3.0'
}
```

## Sample code:

### ViewHolder
First, Create your *ViewHolder* and extend it with *BaseViewHolder<YourModelHere>* with params:
    -> Your ViewDataBinding class
    -> BaseActionCallback : note that you can add your custom action callback (must implements BaseActionCallback)
    as below
```kotlin
class StoryViewHolder(private val viewDataBinding: RowStoryBinding, actionCallback: BaseActionCallback?) :
    BaseBindingViewHolder<StoryModel>(viewDataBinding, actionCallback) {

    init {
        // Attach click listeners (vararg..) with the BaseActionCallback
        attachClickListener(itemView)
    }

    override fun onBind(listItem: StoryModel) {
        viewDataBinding.setVariable(BR.model, listItem)
        viewDataBinding.executePendingBindings()
    }
}

class UserCardViewHolder(private val viewDataBinding: RowUserCardBinding, actionCallback: MyActions?) :
    BaseBindingViewHolder<UserCardModel>(viewDataBinding, actionCallback) {

    init {
        // You can also use this way to attach click listener or you can use custom callbacks such as MyActions
        // attachClickListener(itemView, viewDataBinding.ivProfile, viewDataBinding.tvName)

        viewDataBinding.ivProfile.setOnClickListener {
            // Trigger callback from your custom interface
            actionCallback?.myAction1()
        }
        viewDataBinding.tvName.setOnClickListener {
            actionCallback?.myAction2()
        }
    }

    override fun onBind(listItem: UserCardModel) {
        viewDataBinding.setVariable(BR.model, listItem)
        viewDataBinding.executePendingBindings()
    }
}

// Custom actions interface
interface MyActions : BaseActionCallback {
    fun myAction1()
    fun myAction2()
}
```

### ListItemTypes 
Second, Create a object class and name it 'MyListItemTypes', this class will hold the types of the *ViewHolders* that will be used in the app.
Note that you can create many files of this
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
}
```

### Models
Third, let your *Model* implements *ListItem* and override the *ListItemType* variable
```kotlin
class StoryModel(
    val id: String,
    val imageUrl: String

) : ListItem {
    override var listItemType: ListItemType? = MyListItemTypes.ITEM_STORY
}

class UserCardModel(
    val name: String,
    val phoneNumber: String,
    val location: String,
    val imageUrl: String

) : ListItem {
    override var listItemType: ListItemType? = MyListItemTypes.ITEM_USER_CARD
}
...
```
**NOTE: if the listItemType for a model is *NULL* then the viewHolder will not be loaded only.. without any *CRASH* :)**


### Initializing the adapter
Create an instance from GeneralBindingListAdapter and bind it to your recyclerview
```kotlin
private val adapter: GeneralBindingListAdapter by lazy {
    GeneralBindingListAdapter(context = this, actionCallback = this)
}
```

### Recyclerview
Bind your recyclerview with the adapter
```kotlin
recyclerview?.adapter = adapter
```

### Display the items
Now you just need to add any model to the adapter and it will be added to the adapter with its ViewHolder, and that's it :)
You can mix all the types together and it will be handled automatically by the adapter
```kotlin
val myList = listOf(
    UserCardModel(),
    StoryViewHolder()
    ...
)
adapter.addAll(myList)
```

### Item click callback
```kotlin
override fun onItemClicked(view: View, listItem: ListItem, position: Int, actionId: Int) {
    when (listItem) {
        is UserCardModel -> toast(listItem.text)
        is StoryViewHolder -> toast(listItem.imageUrl)
    }
}
//
// ... other custom callbacks if exists
```

### LoadMore
```kotlin
// Setup the loadmore
adapter.setupLoadMore(loadMoreListener, pageSize)

// Callback of the loadmore
override fun onLoadMore(pageToLoad: Int) {
    // Your code...
}
```

### Custom LoadMore behavior
if you want to add your own behavior when loadmore triggers then you can do as below

```kotlin
// Setup the loadmore
adapter.setupLoadMore(loadMoreListener = this, autoShowLoadingItem = false, pageSize = 20, loadingThreshold = 3)

// Callback of the loadmore
override fun onLoadMore(pageToLoad: Int) {
    // Show Loading
    // Your code...
}

override fun onLoadMoreFinished() {
    super.onLoadMoreFinished()
    // Hide Loading
    // Your code...
}
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
    
